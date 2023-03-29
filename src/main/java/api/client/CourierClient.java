package api.client;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient {

    private static final String COURIER_URI = "https://qa-scooter.praktikum-services.ru/api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse CreateCourier(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(COURIER_URI)
                .then();
    }

    @Step("Удаление курьера")
    public Response DeleteCourier(int id) {
         return given()
                .delete(COURIER_URI + id);
    }

    @Step("Логин (авторизация) курьера")
    public ValidatableResponse Login(Object body) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(body)
                .when()
                .post(COURIER_URI+"login/")
                .then();
    }
}
