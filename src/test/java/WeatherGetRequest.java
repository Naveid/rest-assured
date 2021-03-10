import com.google.gson.Gson;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.print.attribute.standard.RequestingUserName;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.*;

public class WeatherGetRequest {

    //simple get to get weather request
    //@Test
    public void Test_01(){
        Response resp=when().get("https://samples.openweathermap.org/data/2.5/weather?q=London&appid=b6907d289e10d714a6e88b30761fae2");
        System.out.println( resp.getStatusCode());
        Assert.assertEquals(resp.statusCode(),200);

    }
    //@Test
    public void Test_02(){
        Response resp=when().get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=b6907d289e10d714a6e88b30761fae22");
        System.out.println( resp.getStatusCode());
        Assert.assertEquals(resp.statusCode(),401);

    }
// How to use parameters with rest assured
    @Test
public void Test_03() {

        Response resp = given().param("q", "London").
                param("appid", "b6907d289e10d714a6e88b30761fae22").
                when()
                .get("http://samples.openweathermap.org/data/2.5/weather");
        System.out.println(resp.getStatusCode());
        Assert.assertEquals(resp.statusCode(), 200);

    }
    @Test
    public void Test_04() {

        given().param("q", "London").
                param("appid", "b6907d289e10d714a6e88b30761fae22").
                when()
                .get("http://samples.openweathermap.org/data/2.5/weather").
                then().assertThat().statusCode(200);
        //System.out.println(resp.getStatusCode());
        //Assert.assertEquals(resp.statusCode(), 200);
    }

    @Test
    public void Test_05() {

      Response resp =  given().param("q", "London").
                param("appid", "b6907d289e10d714a6e88b30761fae22").
                when()
                .get("http://samples.openweathermap.org/data/2.5/weather");
        System.out.println(resp.asString());
        //System.out.println(resp.getStatusCode());
        //Assert.assertEquals(resp.statusCode(), 200);
    }
@Test
    public void Test_06() {
//weather by city id
        Response resp = given().param("id", "2172797").
                param("appid", "b6907d289e10d714a6e88b30761fae22").
                when()
                .get("http://samples.openweathermap.org/data/2.5/weather");
        System.out.println(resp.asString());
        Assert.assertEquals(resp.getStatusCode(),200);
    }
    @Test
    public void Test_07() {
//weather by city id
        String  GrsReviewIds =String.valueOf( given().param("locale", "en_GB").
                param("pageSize", "5").
                param("pageNumber","4").
                param("sortBy","POST_DATE_DESC").
                when()
                .get("http://guestreviewservice.staging.hcom/v1/properties/335698/reviews").
                        then().contentType(ContentType.JSON).extract().path("reviewId"));
        System.out.println("GrsReviewIds--> "+GrsReviewIds);
     //   curl -X GET "http://guestreviewservice.staging.hcom/v1/properties/335698/reviews?applyEmbargo=false&
        // languageTypes=ALL&locale=en_GB&minReviewLength=0&pageNumber=1&pageSize=5&sortBy=RELEVANCE_ASC&tripType=ALL" -H "accept: application/json" -H "X-Application-ID: test-app" -H "Content-Type: application/json"
        // -d "{\"reviewsRelevance\":{\"37069330\": \"0.9\"}}"
        Map<String, Object> userDetails = new HashMap<>();
        Map<String, Object> details = new HashMap<>();



        Map<Integer, Double> reviewRelevance = new HashMap<>();
        reviewRelevance.put(37069330,0.9);
        System.out.println(reviewRelevance);
        Gson gson = new Gson();
        String json = gson.toJson(reviewRelevance);
        System.out.println(json);

Map<String,Object> check = new HashMap<>();
check.put("reviewsRelevance", reviewRelevance);
        String json1 = gson.toJson(check);
        System.out.println("ppp:"+json1);




        String data ="{\"reviewsRelevance\":"+json+"}";
        System.out.println(data);
        String  GrsAlpsReviewIds =String.valueOf( given().param("locale", "en_GB").
                param("pageSize", "10").
                param("applyEmbargo","false").
                param("languageTypes","ALL").
                param("minReviewLength","0").
                param("pageNumber","1").
                param("sortBy","RELEVANCE_ASC").
                param("tripType","ALL").
                body(json1).
                //param("\"reviewsRelevance\":",json1).
                //param("reviewRelevance",reviewRelevance).
                when()
                .get("http://guestreviewservice.staging.hcom/v1/properties/335698/reviews").
                        then().
                        contentType(ContentType.JSON).extract().path("reviewId"));
        System.out.println("GrsAlpsReviewIds--> "+GrsAlpsReviewIds);



    }






    @Test
    public void Test_08() {
//weather by city id

        Response resp = given().param("id", "2172797").
                param("appid", "1f7d013bdfad3da7032d66f7a5c4ca53").
                when()
                .get("http://api.openweathermap.org/data/2.5/weather");
        String lon= String.valueOf(resp.
                        then().contentType(ContentType.JSON).extract().path("coord.lon"));
        String lat = String.valueOf(resp.
                        then().contentType(ContentType.JSON).extract().path("coord.lat"));

        Response resp1 = given().param("lat",lat )
                .param("lon",lon).
                        param("appid", "1f7d013bdfad3da7032d66f7a5c4ca53").when().get("http://api.openweathermap.org/data/2.5/weather");

        String weatherReport = resp.
                        then().contentType(ContentType.JSON).extract().path("weather[0].description");
        System.out.println(weatherReport);

        String weatherReportLatLong = resp1.
                        then().contentType(ContentType.JSON).extract().path("weather[0].description");
        System.out.println(weatherReportLatLong);

        Assert.assertEquals(weatherReport, weatherReportLatLong, "Data doesnot match");
    }





}
