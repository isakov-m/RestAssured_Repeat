import Model.Location;
import Model.Place;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class _03_ApiTestPOJO {

    // POJO : JSON nesnesi : locationNesnesi


    @Test
    public void extractPOJO() {
        // Ogrenci ogr1 = new Ogrenci();

        Location locationNesnesi =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().body().as(Location.class)    // Location kalibina gore
                // donen body bilgisi   Location Class kalibiyla cevir
                ;

        System.out.println("locationNesnesi = " + locationNesnesi);
        System.out.println("locationNesnesi.getCountry() = " + locationNesnesi.getCountry());
        System.out.println("locationNesnesi.getPlaces() = " + locationNesnesi.getPlaces());

        for (Place p : locationNesnesi.getPlaces())
            System.out.println("p = " + p);


        // JSonaDonustur(locationNesnesi); developer bu şekilde dönüştürmüştü
        // Json.Serialise(locationNesnesi);  bende tersine deSerialize yaptım.
        // yani NESNE yi elde ettim.
    }


    @Test
    public void extractPOJO_Soru() {
        // http://api.zippopotam.us/tr/01000  endpointinden dönen verilerden "Dörtağaç Köyü" ait bilgileri yazdırınız

        Location ln =
                given()
                        .when()
                        .get("http://api.zippopotam.us/tr/01000")

                        .then()
                        .extract().body().as(Location.class);

        for (Place p : ln.getPlaces()) {
            if (p.getPlacename().equalsIgnoreCase("Dörtağaç Köyü"))
                System.out.println("p = " + p);
        }

    }

}
