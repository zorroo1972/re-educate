import java.lang.reflect.Proxy;

import static java.lang.reflect.Proxy.newProxyInstance;

public class Main {
    public static void main(String[] args) {
        Fraction fr = new Fraction(2, 3);
        Fractionable num = cache(fr);
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(5);
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(2);
        num.doubleValue();// sout молчит
        num.doubleValue();// sout молчит

        //Thread.sleep(1500);
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит
    }
    public static <T> T cache(T value) {
        ClassLoader ClassLoader = value.getClass().getClassLoader();
        Class[] interfaces = value.getClass().getInterfaces();
        Object  proxy  =  Proxy.newProxyInstance(ClassLoader, interfaces, new CacheInvocationHandler(value));
        return (T) proxy;
    }
}
