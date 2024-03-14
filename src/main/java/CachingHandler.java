import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;

public class CachingHandler<T> implements InvocationHandler {
    private T currentObject;
    private Map<Method, Object> results = new HashMap<>();

    public CachingHandler(T currentObject) {
        this.currentObject = currentObject;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object objectResult;
        Method currentMethod;
        Thread thread;
        Thread thread1;
        currentMethod = currentObject.getClass().getMethod(method.getName(), method.getParameterTypes());

        if (currentMethod.isAnnotationPresent(Cache.class)) {
            if (results.containsKey(currentMethod)) {
                return results.get(currentMethod);//3
            }
            objectResult = method.invoke(currentObject, args);
            results.put(currentMethod, objectResult);//4

          //  thread = new Thread(() -> results.put(currentMethod, objectResult));

          // thread.run();
           return objectResult;
        }
        if (currentMethod.isAnnotationPresent(Mutator.class)) {
            //results.clear(); //2
            thread1 = new Thread(() ->results.clear());
            thread1.run();
        }
        return method.invoke(currentObject, args);
    }


    private static class CacheKey {
        String key;
        Method method;

        public CacheKey(String key, Method method) {
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
        public boolean isActuat() {
            return (expirationTime == null || this.expirationTime >= System.currentTimeMillis());
        }
        public boolean notActual() {
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
}




