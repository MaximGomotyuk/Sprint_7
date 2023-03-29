package api.client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient {

    private static final String ORDERS_URI = "https://qa-scooter.praktikum-services.ru/api/v1/orders/";

    @Step("Создание заказа")
    public ValidatableResponse CreateOrder(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(ORDERS_URI)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse GetOrdersList() {
        return given()
                .get(ORDERS_URI)
                .then();
    }

}
