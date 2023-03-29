import api.client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class TestCreateCourier {

    private int id;
    private String login = RandomStringUtils.randomAlphabetic(10);
    private String pass = RandomStringUtils.randomAlphabetic(10);
    private String name = RandomStringUtils.randomAlphabetic(10);
    private String json = "{\"login\": \"" + this.login + "\", \"password\": \"" + this.pass + "\", " +
            "\"firstName\": \"" + this.name + "\"}";

    @After
    public void removeCourier() {
        CourierClient courierClient = new CourierClient();
        courierClient.DeleteCourier(id);
    }

    @Test
    @DisplayName("Можно создать курьера")
    public void createNewCourier() {
        CourierClient courierClient = new CourierClient();
        courierClient.CreateCourier(json).statusCode(201).and().assertThat().body("ok", equalTo(true));
        this.id = courierClient.Login(json).extract().path("id");
    }
}
