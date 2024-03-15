import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CachingHandler implements InvocationHandler {
    private final Object currentObject;
    private Map<CacheKey, CacheRes> cache = new ConcurrentHashMap<>();

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
        CacheRes entry = null;
        Method currentMethod = currentObject.getClass().getMethod(method.getName(), method.getParameterTypes());
        CacheKey key = new CacheKey(currentObject, method);
        for (Map.Entry<CacheKey, CacheRes> ch : cache.entrySet()){
           // System.out.println("Create new scratch file from selection = "+ch.getValue().result);
          //  System.out.println("entry.result = " + entry.result);
            if (ch.getKey().key.equals(currentObject) &&entry!= null && ch.getValue().result == entry.result) {
                    entry = cache.get(ch.getKey());
            }
        }
        //System.out.println("entry  = "+ entry + "  currentObject ="+ currentObject);
        if (currentMethod.isAnnotationPresent(Cache.class)) {
            var cacheTime = currentMethod.getAnnotation(Cache.class).value();
            if (entry == null || !entry.isActual()) {
                Object objectResult = method.invoke(currentObject, args);
                entry = new CacheRes(objectResult, cacheTime);
                cache.put(key.clone(), entry);
                //System.out.println("Calculation  = "+ cache);
                System.out.println("entryrezalt  = "+ entry.result);
            } else {
                entry.refreshExpiration(cacheTime);
                System.out.println("Return cache  = "+ entry.getResult());

            }
            return entry.getResult();
        }

        if (currentMethod.isAnnotationPresent(Mutator.class)) {
           System.out.println("Annotation Mutator");
          //  System.out.println("entry  = "+ entry);
         //   new Thread(()-> cleanUpCache()).start();

        }
        System.out.println("Ca = "+ cache);
        return method.invoke(currentObject, args);
    }
    private static class CacheKey implements Cloneable{
        Object key;
        Method method;

        public CacheKey(Object key, Method method) {
            this.key = key;
            this.method = method;
        }
        @Override
        public String toString() {
            return "CacheKey{" +
                    "key=" + key +
                    ", method=" + method +
                    '}';
        }
        @Override
        protected CacheKey clone() throws CloneNotSupportedException {
            return (CacheKey) super.clone();
        }
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




