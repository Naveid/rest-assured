import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import testpojo.Weather;
import testpojo.Weatherapi;

import javax.print.attribute.standard.RequestingUserName;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
        System.out.println(resp.getTimeIn(TimeUnit.SECONDS));
        //Way 1 Creating Pojo files
        Weatherapi weatherapi= resp.as(Weatherapi.class, ObjectMapperType.GSON);
        String icon="";
        SoftAssert softAssert = new SoftAssert();
        List<Weather> po =weatherapi.getWeather();
        for(Weather weather:po){
            icon =weather.getIcon();

            System.out.println(weather.toString());
            softAssert.assertTrue(!weather.getDescription().equalsIgnoreCase(""));
        }
        softAssert.assertAll();



        System.out.println(resp.getStatusCode());
        Assert.assertEquals(resp.statusCode(), 200);
        resp.getBody().prettyPrint();

        //Way 2 usinh JsonPath.getString
        String ab =resp.jsonPath().getString("visibility");
        System.out.println("visibility-->"+ab);
        HashMap<String,Integer> ah = new HashMap<String, Integer>();
        ab=resp.jsonPath().getString("main");
        System.out.println("temp-->"+ab);


        //Way 3 using .then.extarct.path
        List<HashMap<String,Integer>> abc =resp.then().extract().path("weather");
        for(int k=0;k<abc.size();k++){
            System.out.println(abc.get(k).get("description"));
        }

        //Way 4<->2  using JsonPath class object
        JsonPath jsonPath = resp.jsonPath();
        List<HashMap<String,String>> abcs=jsonPath.get("weather");


        for(int k=0;k<abc.size();k++){
            System.out.println("in JsonPath");
            if(abcs.get(k).get("description").equals("light intensity drizzle")) {
            Assert.assertEquals(abcs.get(k).get("icon"),icon,"values not matched");
            }

        }

        JSONObject object=new JSONObject(resp.getBody().asString());

        JSONArray array=object.getJSONArray("weather");
        System.out.println(array.length());
    for(int i =0;i<array.length();i++){
           JSONObject jsonObject = array.getJSONObject(i);

           System.out.println(jsonObject.get("icon"));
    }
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
public void googlebooks(){
        Response resp=given().param("q","Islam").when().get("https://www.googleapis.com/books/v1/volumes");

        JSONObject object=new JSONObject(resp.getBody().asString());
        JSONArray items=object.getJSONArray("items");
        System.out.println("Total records displayed -->"+items.length());


            JSONObject volumeInfo = ((JSONObject) items.get(0)).getJSONObject("volumeInfo");
        System.out.println("tittle-->"+volumeInfo.optString("title"));
        JSONArray authorsArray = volumeInfo.optJSONArray("authors");
        System.out.println(authorsArray.length() );

//        for (int i=0;i< items.length();i++){
//            //System.out.println(items.get(i));
//            JSONObject innerJson = items.getJSONObject(i);
//            System.out.println(innerJson.get("volumeInfo"));

//           JSONArray volumeinfo = innerJson.getJSONArray("volumeInfo");
//            for (int c=0;c< volumeinfo.length();i++) {
//                JSONObject innerJsonVi = volumeinfo.getJSONObject(i);
//                System.out.println("Title--->" + innerJsonVi.get("title"));
//            }
        //}
       //JsonArray volumeinfo = items.getJSONArray("volumeInfo");
        resp.prettyPrint();
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
   // @Test
//    public void Test_07() {
////weather by city id
//        String  GrsReviewIds =String.valueOf( given().param("locale", "en_GB").
//                param("pageSize", "5").
//                param("pageNumber","4").
//                param("sortBy","POST_DATE_DESC").
//                when()
//                .get("http://guestreviewservice.staging.hcom/v1/properties/335698/reviews").
//                        then().contentType(ContentType.JSON).extract().path("reviewId"));
//        System.out.println("GrsReviewIds--> "+GrsReviewIds);
//     //   curl -X GET "http://guestreviewservice.staging.hcom/v1/properties/335698/reviews?applyEmbargo=false&
//        // languageTypes=ALL&locale=en_GB&minReviewLength=0&pageNumber=1&pageSize=5&sortBy=RELEVANCE_ASC&tripType=ALL" -H "accept: application/json" -H "X-Application-ID: test-app" -H "Content-Type: application/json"
//        // -d "{\"reviewsRelevance\":{\"37069330\": \"0.9\"}}"
//        Map<String, Object> userDetails = new HashMap<>();
//        Map<String, Object> details = new HashMap<>();
//
//
//
//        Map<Integer, Double> reviewRelevance = new HashMap<>();
//        reviewRelevance.put(37069330,0.9);
//        System.out.println(reviewRelevance);
//        Gson gson = new Gson();
//        String json = gson.toJson(reviewRelevance);
//        System.out.println(json);
//
//Map<String,Object> check = new HashMap<>();
//check.put("reviewsRelevance", reviewRelevance);
//        String json1 = gson.toJson(check);
//        System.out.println("ppp:"+json1);
//
//
//
//
//        String data ="{\"reviewsRelevance\":"+json+"}";
//        System.out.println(data);
//        String  GrsAlpsReviewIds =String.valueOf( given().param("locale", "en_GB").
//                param("pageSize", "10").
//                param("applyEmbargo","false").
//                param("languageTypes","ALL").
//                param("minReviewLength","0").
//                param("pageNumber","1").
//                param("sortBy","RELEVANCE_ASC").
//                param("tripType","ALL").
//                body(json1).
//                //param("\"reviewsRelevance\":",json1).
//                //param("reviewRelevance",reviewRelevance).
//                when()
//                .get("http://guestreviewservice.staging.hcom/v1/properties/335698/reviews").
//                        then().
//                        contentType(ContentType.JSON).extract().path("reviewId"));
//        System.out.println("GrsAlpsReviewIds--> "+GrsAlpsReviewIds);
//
//
//
//    }
//
//
//



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
