import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CachingHandler implements InvocationHandler {
    private final Object currentObject;
    private Map<Method,CacheRes> resMap;
    private Map<Integer, Map> cache = new ConcurrentHashMap<>();

    public CachingHandler(Object currentObject) {
        this.currentObject = currentObject;
    }

    private void cleanUpCache() {
        synchronized (resMap) {
            resMap.entrySet().removeIf(entry -> entry.getValue().notActual());
        }
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        CacheRes entry = null;
        Integer key = currentObject.hashCode();
        Method currentMethod = currentObject.getClass().getMethod(method.getName(), method.getParameterTypes());
        if (key != null) resMap = cache.get(key);
        if (resMap != null) entry = resMap.get(method);
        if (currentMethod.isAnnotationPresent(Cache.class)) {
           var cacheTime = currentMethod.getAnnotation(Cache.class).value();
            if ( entry == null || !entry.isActual()) {
                Object objectResult =  method.invoke(currentObject, args);
                entry = new CacheRes(objectResult, cacheTime);
                resMap = new ConcurrentHashMap<>();
                resMap.put(method,entry);
                cache.put(key, resMap);
            } else {
                entry.refreshExpiration(cacheTime);
                System.out.println("Return cache  = "+ entry.getResult());
            }
            return entry.getResult();
        }

        if (currentMethod.isAnnotationPresent(Mutator.class)) {
           System.out.println("Annotation Mutator");
            new Thread(()-> cleanUpCache()).start();
        }
        return method.invoke(currentObject, args);
    }
    private class CacheRes {
        Object result;
        Integer cacheTime;
        Long expTime;

        public CacheRes(Object result, Integer cacheTime) {
            this.result = result;
            if (this.cacheTime != null) {
                this.expTime = System.currentTimeMillis() + this.cacheTime;
            }
        }
        public Object getResult()  {
            return result ;
        }
        public boolean isActual() {
            return (expTime == null || this.expTime >= System.currentTimeMillis());
        }
        public boolean notActual() {
            return (expTime != null && this.expTime <= System.currentTimeMillis());
        }
        public void refreshExpiration(Integer cacheTime) {
            if (cacheTime != null) {
                this.expTime = System.currentTimeMillis() + cacheTime;
            }
        }
        @Override
        public String toString() {
            return "CacheEntry{" +
                    "result=" + result +
                    ", cacheTime=" + cacheTime +
                    ", expTime=" + expTime +
                    '}';
        }

    }
}




