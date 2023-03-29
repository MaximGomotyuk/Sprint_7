import api.client.OrdersClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class ParamTestCreateOrder {

    private String firstColor;
    private String secondColor;

    public ParamTestCreateOrder(String fColor, String sColor) {
        this.firstColor = fColor;
        this.secondColor = sColor;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{{"Black", "Grey"}, {"Black", null}, {"Grey", null}, {null, null}};

    }

    @Test
    @DisplayName("Проверка создания заказа с разными цветами самоката")
    public void checkCreateOrderWithDiffColor() {
        String json = "{\"firstName\": \"Петя\", \"lastName\": \"Васечкин\", " + "\"address\": \"Красная площадь, д.1\"," +
                " \"metroStation\": 4, \"phone\": \"+7 800 355 35 35\"," + " \"rentTime\": 2, \"deliveryDate\": \"2023-03-20\", " +
                "\"comment\": \"Быстрее!\", " + "\"color\": [\"" + this.firstColor + "\", \"" + this.secondColor + "\"]}";
        OrdersClient ordersClient = new OrdersClient();
        ValidatableResponse testCreateOrderStatusOk = ordersClient.CreateOrder(json);
        testCreateOrderStatusOk.statusCode(201).and().assertThat().body("track", notNullValue());
    }
}