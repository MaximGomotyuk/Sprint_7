import org.junit.Test;
import static io.restassured.RestAssured.given;


public class TestOrdersList {

       @Test
    public void getOrdersList() {
         given()
                .get("https://qa-scooter.praktikum-services.ru/api/v1/orders")
                .then().statusCode(200)
                .extract()
                .path("orders", "id");
    }

}
