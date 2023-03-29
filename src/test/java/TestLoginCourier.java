import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestLoginCourier {

    private int id;
    private String login = "login";
    private String pass = "login";
    private String name;
    private String logJson = "{\"login\": \"" + login + "\", \"password\": \"" + pass + "\"}";

    @Before
    public void SetUp() {

        this.login = RandomStringUtils.randomAlphabetic(10);
        this.pass = RandomStringUtils.randomAlphabetic(10);
        this.name = RandomStringUtils.randomAlphabetic(10);
        String json = "{\"login\": \"" + this.login + "\", \"password\": \"" + pass + "\", " +
                "\"firstName\": \"" + name + "\"}";

        CourierClient courierClient = new CourierClient();
        courierClient.CreateCourier(json);
        this.id = courierClient.Login(json).extract().path("id");
    }

    @After
    public void removeCourier() {
        CourierClient courierClient = new CourierClient();
        courierClient.DeleteCourier(id);
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void courierCanBeAuthorized() {
        CourierClient courierClient = new CourierClient();
        courierClient.Login(logJson).extract().path("id");
    }

    @Test
    @DisplayName("Курьер НЕ может авторизоваться с несуществующим логином")
    public void exceptionIfWrongLogin() {
        this.login = RandomStringUtils.randomAlphabetic(10);
        CourierClient courierClient = new CourierClient();
        courierClient.Login(logJson).statusCode(404).and().assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Курьер НЕ может авторизоваться без логина")
    public void exceptionWithoutLogin() {
        String lJson = "{\"login\": null, \"password\": \"12345\"}";
        CourierClient courierClient = new CourierClient();
        courierClient.Login(lJson).statusCode(400).and().assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}
