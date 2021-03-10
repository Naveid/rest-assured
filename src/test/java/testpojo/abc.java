package testpojo;

/**
 * Created by Naved on 28-Feb-21.
 */
public class abc {
    public Float a;
    public Float b;

    public abc(Float a, Float b) {
        this.a = a;
        this.b = b;
    }

    public abc() {
    }

    @Override
    public String toString() {
        return "name{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    public Float getA() {
        return a;
    }

    public Float getB() {
        return b;
    }

    public void setB(Float b) {
        this.b = b;
    }

    public void setA(Float a) {
        this.a = a;
    }

}
