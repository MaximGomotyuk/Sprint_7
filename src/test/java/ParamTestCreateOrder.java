
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class ParamTestCreateOrder {

    String grey;
    String black;

    public ParamTestCreateOrder (String fColor, String sColor){
        this.grey = fColor;
        this.black = sColor;
    }
    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {"Black", "Grey"},
                {"Black", null},
                {"Grey", null},
                {null, null}
        };
    }
    @Test
    public void checkCreateOrderWithDiffColor () {
        String json = "{\"firstName\": \"Петя\", \"lastName\": \"Васечкин\", " +
                "\"address\": \"Красная площадь, д.1\", \"metroStation\": 4, \"phone\": \"+7 800 355 35 35\"," +
                " \"rentTime\": 2, \"deliveryDate\": \"2023-03-20\", \"comment\": \"Быстрее!\", " +
                "\"color\": [\"" + this.grey + "\", \"" + this.black + "\"]}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("https://qa-scooter.praktikum-services.ru/api/v1/orders")
                .then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);
    }
}