import api.client.OrdersClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class TestOrdersList {

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrdersList() {
        OrdersClient ordersClient = new OrdersClient();
        ValidatableResponse getOrderListSuccess = ordersClient.GetOrdersList();
        getOrderListSuccess.statusCode(200).extract().path("orders", "id");
    }
}
