public class Fraction implements Fractionable {
    private int num;
    private int denum;

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Mutator
    public void setNum(int num) {
        this.num = num;
        System.out.println("Set num =  " +   this.num);
    }

    @Mutator
    public void setDenum(int denum) {
        this.denum = denum;
    }

    @Override
    public void introduce() {

        System.out.println("Дробь " + this);
    }

    @Cache
    public double doubleValue() {
       System.out.println("Origin double value  " +  (double) num / denum);
        return (double) num / denum;
    }

    @Override
    public String toString() {
        return "Fraction{" +
                "num=" + num +
                ", denum=" + denum +
                '}';
    }
}



