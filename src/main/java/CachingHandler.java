import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CachingHandler implements InvocationHandler {
    private final Object currentObject;
    private Map<Integer, CacheRes> cache = new ConcurrentHashMap<>();

    public CachingHandler(Object currentObject) {
        this.currentObject = currentObject;
    }

    private void cleanUpCache() {
        //System.out.println("Clean not actual");
        synchronized (cache) {
            cache.entrySet().removeIf(entry -> entry.getValue().notActual());
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        int key = currentObject.hashCode();
        Method currentMethod = currentObject.getClass().getMethod(method.getName(), method.getParameterTypes());
        new Thread(()-> cleanUpCache()).start();
        CacheRes entry = cache.get(key);
        if (currentMethod.isAnnotationPresent(Cache.class)) {
           var cacheTime = currentMethod.getAnnotation(Cache.class).value();
            if ( entry == null || !entry.isActual()) {
                Object objectResult =  method.invoke(currentObject, args);
                entry = new CacheRes(objectResult, cacheTime);
                cache.put(key, entry);
            } else {
                entry.refreshExpiration(cacheTime);
                System.out.println("Return cache  = "+ entry.getResult());
            }
            return entry.getResult();
        }

        if (currentMethod.isAnnotationPresent(Mutator.class)) {
           System.out.println("Annotation Mutator");
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




