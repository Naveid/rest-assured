package testpojo;

import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.jayway.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.path.json.JsonPath.*;



/**
 * Created by Naved on 28-Feb-21.
 */
public class Employee {
    public static emparr emparr;

    public Employee() {
    }
    public Employee(emparr emparr) {
        this.emparr = emparr;
    }



    @Override
    public String toString() {
        return "{\"hire\":"+emparr+"}";
    }

    public static testpojo.emparr getEmparr() {
        return emparr;
    }

    public static void setEmparr(testpojo.emparr emparr) {
        Employee.emparr = emparr;
    }

    @Test(threadPoolSize = 3)

    public void test(){
        ArrayList<empdetails>abc = new ArrayList();
        empdetails emp1= new empdetails("nav",1);
        abc.add(emp1);
        empdetails emp2=new empdetails("del",2);
        abc.add(emp2);
        empdetails emp3= new empdetails("sss",3);
        abc.add(emp3);
        emparr em = new emparr(abc);
         //emparr em1 = new emparr(emp2)
       Employee e = new Employee(em);
        System.out.println(e.toString());
        //System.out.println(emp1.toString());
        Response resp = given().param("q", "London").
                param("appid", "b6907d289e10d714a6e88b30761fae22").
                when()
                .get("http://samples.openweathermap.org/data/2.5/weather");
        System.out.println(resp.getStatusCode());
        resp.prettyPrint();
        Assert.assertEquals(resp.statusCode(), 200);
        JSONObject resp1 = new JSONObject(resp.getBody().asString());
       String check =  resp1.getJSONObject("coord").get("lat").toString();



       // String lat = resp1.get("resp1.coord.lat").toString();

        System.out.println(check);


     //   abc abcd = resp.as(abc.class);
       // System.out.println(abcd.getA());
    }
}
