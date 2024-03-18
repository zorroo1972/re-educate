public class Main {
    public static void main(String[] args) throws InterruptedException {

        Fraction fr = new Fraction(2, 3);
        Fractionable num = Utils.cache(fr);

        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(5);
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит

        num.setNum(2);
        num.doubleValue();// sout молчит
        num.doubleValue();// sout молчит

        Thread.sleep(1500);
        num.doubleValue();// sout сработал
        num.doubleValue();// sout молчит


    }
}
