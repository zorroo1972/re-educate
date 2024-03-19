
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

    @Cache(1000)
    public double doubleValue() {
       System.out.println("Origin double value  " +  (double) num / denum);
        return (double) num / denum;
    }

    @Override
    @Cache(1000)
    public String toString() {
        return "Fraction{" +
                "num=" + num +
                ", denum=" + denum +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();

    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Fraction fraction = (Fraction) object;

        if (num != fraction.num) return false;
        return denum == fraction.denum;
    }

    @Override
    public int hashCode() {
        int result = num;
        result = 31 * result + denum;
        return result;
    }
}



