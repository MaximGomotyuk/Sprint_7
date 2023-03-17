import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class TestLoginCourier {
    String login = "login";
    String pass = "login";
    String name;
    String logJson = "{\"login\": \""+login+"\", \"password\": \""+pass+"\"}";

    @Before
    public void SetUp () {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        this.login = RandomStringUtils.randomAlphabetic(10);
        this.pass = RandomStringUtils.randomAlphabetic(10);
        this.name = RandomStringUtils.randomAlphabetic(10);
        String json = "{\"login\": \"" + this.login + "\", \"password\": \"" + pass + "\", " +
                "\"firstName\": \"" + name + "\"}";

        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier");
          }

    @Test
    public void courierCanBeAuthorized () {
        given()
                .header("Content-type", "application/json")
                .and()
                .body(logJson)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .extract()
                .path("id");

    }
    @Test
    public void exceptionIfWrongLogin () {
        this.login = RandomStringUtils.randomAlphabetic(10);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(logJson)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    public void exceptionWithoutLogin () {
        String lJson = "{\"login\": null, \"password\": \"12345\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(lJson)
                .when()
                .post("/api/v1/courier/login")
                .then()//.statusCode(400)
               // .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}
