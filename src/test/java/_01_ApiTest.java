import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class _01_ApiTest {

    @Test
    public void test1() {

        given()
                //Hazirlik islemleri kodlari
                .when()
                // endpoint (url),metod u verip istek gonderiliyor

                .then();
        // assertion, test, data islemleri
        ;
    }


    @Test
    public void statusCodeTest() {

        given()
                // hazirlik kismi bos
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()   // donen body json data, log().All() : gidip gelen her sey
                .statusCode(200)    // test kismi oldugundan assertion status code 200 mu?
        ;
    }

    @Test
    public void contentTypeTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)      // donen datanin tipi JSON mi?
        ;
    }

    @Test
    public void chechCountryInResponseBody() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)    // assertion
                .body("country", equalTo("United States"))   // assertion
        // body nin country degiskeni "United States" esit MI
        ;
    }


    @Test
    public void chechStateInResponseBody() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu doğrulayınız

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                //.log().body()
                .statusCode(200) // assertion
                .body("places[0].state", equalTo("California")) // assertion
        // body nin country degiskeni " United States" esit Mi
        ;
    }


    @Test
    public void checkjHasItem() {
        // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
        // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
        // olduğunu doğrulayınız

        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                //.log().body()
                .body("places.'place name'", hasItem("Dörtağaç Köyü"))
                .statusCode(200)
        ;
    }


    @Test
    public void bodyArrayHasSizeTest() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .body("places", hasSize(1))   // places in item size i 1 e esit MI
                .statusCode(200)
        ;
    }


    @Test
    public void bodyArrayHasSizeTest2() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .body("places.size()", equalTo(1))   // places in item size i 1 e esit MI
                .statusCode(200)
        ;
    }


    @Test
    public void combiningTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .statusCode(200)
                .body("places", hasSize(1))
                .body("places[0].state", equalTo("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
        ;
    }


    @Test
    public void pathParamTest() {

        given()

                .pathParam("ulke", "us")
                .pathParam("postaKod", 90210)
                .log().uri()

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKod}")

                .then()
                .statusCode(200)
        ;
    }


    @Test
    public void queryParamTest() {
        //https://gorest.co.in/public/v1/users/?page=3

        given()
                .param("page", 1)    // ?page=1 seklinde linke ekleniyor     // queryParam ile de kullanilabilir
                .log().uri()

                .when()
                .get("https://gorest.co.in/public/v1/users")    // ?page=1

                .then()
                .statusCode(200)
                .log().body()
        ;
    }


    @Test
    public void queryParamTest2() {
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i <= 10; i++) {

            given()
                    .param("page", i)
                    .log().uri()

                    .when()
                    .get("https://gorest.co.in/public/v1/users")

                    .then()
                    .statusCode(200)
                    //.log().body()
                    .body("meta.pagination.page", equalTo(i))
            ;
        }

    }

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {

        baseURI = "https://gorest.co.in/public/v1";

        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI) // log().uri()
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)  // statusCode(200)
                .log(LogDetail.BODY)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void requestResponseSpecification() {

        given()
                .param("page", 1)
                .spec(requestSpec)

                .when()
                .get("/users")    // http yok ise baseUri baş tarafına gelir.

                .then()
                .statusCode(200)
                .spec(responseSpec)
        ;
    }







}

