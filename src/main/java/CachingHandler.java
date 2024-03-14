import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CachingHandler implements InvocationHandler {
    private final Object currentObject;
    Thread clearCacheThread;
    private Map<CacheKey, CacheEntry> cache = new ConcurrentHashMap<>();

    public CachingHandler(Object currentObject) {
        this.currentObject = currentObject;
    }
    private void cleanUpCache() {
        System.out.println("Clean not actual");
        synchronized (cache) {
            cache.entrySet().removeIf(entry -> entry.getValue().notActual());
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       // new Thread(()-> cleanUpCache()).start();
        CacheKey key = new CacheKey(currentObject, method);
        CacheEntry entry = cache.get(key);
        Object objectResult;
        Method currentMethod;
        currentMethod = currentObject.getClass().getMethod(method.getName(), method.getParameterTypes());

        if (currentMethod.isAnnotationPresent(Cache.class)) {
            int lTime = currentMethod.getAnnotation(Cache.class).value();
            if (entry == null || !entry.isActual()) {
                System.out.println("Annotation Cache New calculation ");
                objectResult = method.invoke(currentObject, args);
                entry = new CacheEntry(objectResult, lTime);
                cache.put(key, entry);
            } else {
                System.out.println("Annotation Cache New time");
                entry.refreshExpiration(lTime);
            }
            return entry.getResult();
        }

        if (currentMethod.isAnnotationPresent(Mutator.class)) {
            System.out.println("Annotation Mutator");
            new Thread(()-> cleanUpCache()).start();

        }
        return method.invoke(currentObject, args);
    }


    private static class CacheKey {
        Object key;
        Method method;

        public CacheKey(Object key, Method method) {
            this.key = key;
            this.method = method;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(key, cacheKey.key) && Objects.equals(method, cacheKey.method);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key,method);
        }
    }
    private class CacheEntry {
        Object result;
        Integer Ltime;
        Long expirationTime;

        public CacheEntry(Object result, Integer ltime) {
            this.result = result;
            if (Ltime != null) {
                this.expirationTime = System.currentTimeMillis() + Ltime;
            }
        }
        public Object getResult() {
            return result;
        }
        public boolean isActual() {
            System.out.println("expirationTime = " + this.expirationTime+"currentTimeMillis"+ System.currentTimeMillis());
            return (expirationTime == null || this.expirationTime >= System.currentTimeMillis());
        }
        public boolean notActual() {
            System.out.println("expirationTime = " + this.expirationTime+"currentTimeMillis"+ System.currentTimeMillis());
            return !(expirationTime == null || this.expirationTime >= System.currentTimeMillis());
        }
        public void refreshExpiration(Integer Ltime) {
            if (Ltime != null) {
                this.expirationTime = System.currentTimeMillis() + Ltime;
            }
        }
        @Override
        public String toString() {
            return "CacheEntry{" +
                    "result=" + result +
                    ", Ltime=" + Ltime +
                    ", expirationTime=" + expirationTime +
                    '}';
        }
    }
    class ConsUtils{
        public static <T> T cache(T objectIncome)  {
            return (T) Proxy.newProxyInstance(
                    objectIncome.getClass().getClassLoader(),
                    objectIncome.getClass().getInterfaces(),
                    new CachingHandler(objectIncome));
        }
    }
}




