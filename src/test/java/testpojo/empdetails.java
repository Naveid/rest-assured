package testpojo;

/**
 * Created by Naved on 28-Feb-21.
 */
public class empdetails {

    public String name;
    public int id;

    public empdetails(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public empdetails() {
    }

    @Override
    public String toString() {
        return "{\"name\":\"" + name + "\",\"Id\":\"" + id +"\"}";
    }
}

