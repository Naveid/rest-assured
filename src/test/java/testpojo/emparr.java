package testpojo;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Naved on 28-Feb-21.
 */
public class emparr {
   public static ArrayList<empdetails> lemp ;

    public emparr(ArrayList lemp) {
        this.lemp=lemp;
    }

    @Override
    public String toString() {
        return "{\"Emp\":"+lemp+"}";
    }

    public static ArrayList<empdetails> getLemp() {
        return lemp;
    }

    public static void setLemp(ArrayList<empdetails> lemp) {
        emparr.lemp = lemp;
    }
}
