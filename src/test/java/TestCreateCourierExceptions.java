import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;


public class TestCreateCourierExceptions {
    private String login = RandomStringUtils.randomAlphabetic(10);
    private String json = "{\"login\": \"" + this.login + "\", \"password\": \"12345\", " +
            "\"firstName\": \"Саша\"}";

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров = с одним логином")
    public void createDoubleNewCourier() {
        CourierClient courierClient = new CourierClient();
        courierClient.CreateCourier(json);
        courierClient.CreateCourier(json).statusCode(409).and().assertThat().body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера без одного из обязательных полей (ждём 400)")
    public void createCourierWithoutLogin() {
        String json = "{\"login\": \"\", \"password\": \"12345\", " +
                "\"firstName\": \"Саша\"}";
        CourierClient courierClient = new CourierClient();
        courierClient.CreateCourier(json).statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}