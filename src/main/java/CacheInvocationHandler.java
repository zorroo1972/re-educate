import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class CacheInvocationHandler  implements InvocationHandler {
    Object obj;
    int countCall = 0;
    boolean isCache = false;
    Object tmp = null;

    public CacheInvocationHandler(Object obj) {
        this.obj = obj;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        boolean annCache = false;
        boolean annMutator = false;
        for (Method meth : obj.getClass().getDeclaredMethods()) {
            if (meth.getName().equals(method.getName())) {
                if (meth.isAnnotationPresent(Cache.class)) {
                    annCache = true;
                } else if (meth.isAnnotationPresent(Mutator.class)) {
                    annMutator = true;
                }
            }
        }
        if (countCall == 0 ) {
            if(annCache) {
                tmp = method.invoke(obj, (args));
                countCall++;
                isCache = true;
            }
            else if (annMutator) {
                isCache = false;
                return method.invoke(obj, (args));
            }
        }
        else {
            if (annCache) {
                if (isCache) {
                    System.out.println("Return cache " + tmp);
                    countCall++;
                    isCache = true;
                }
                else
                {
                    tmp = method.invoke(obj, (args));
                    countCall++;
                    isCache = true;
                }
            }
            else if (annMutator) {
                isCache = false;
                return method.invoke(obj, (args));
            }
        }
        return tmp;
    }
}





