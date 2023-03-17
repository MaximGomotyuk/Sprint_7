import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateCourier {
    int id;
    String login = RandomStringUtils.randomAlphabetic(10);
    String pass = RandomStringUtils.randomAlphabetic(10);
    String name = RandomStringUtils.randomAlphabetic(10);
    String json = "{\"login\": \""+this.login+"\", \"password\": \""+this.pass+"\", " +
            "\"firstName\": \""+ this.name +"\"}";

    @Before
    public void url () {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @After
    public void removeCourier(){
         this.id = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract()
                .path("id");

                given()
                    .delete("/api/v1/courier/:\"" + this.id + "\"");
    }

    @Test
    public void createNewCourier() {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201)
                .and()
                .assertThat().body("ok", equalTo(true));
    }
}
