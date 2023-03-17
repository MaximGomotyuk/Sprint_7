import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class TestCreateCourierExceptions {
    String login = RandomStringUtils.randomAlphabetic(10);
    String json = "{\"login\": \"" + this.login + "\", \"password\": \"12345\", " +
            "\"firstName\": \"Саша\"}";

    @Test //Нельзя создать двух одинаковых курьеров = с одним логином
    public void createDoubleNewCourier() {
         given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier");

        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier")
                .then().statusCode(409)
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test  //Создание курьера без одного из обязательных полей
    public void createCourierWithoutLogin() {
        String json = "{\"login\": \"\", \"password\": \"12345\", " +
                "\"firstName\": \"Саша\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/courier")
                .then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}