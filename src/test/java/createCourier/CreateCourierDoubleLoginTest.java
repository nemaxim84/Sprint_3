package createCourier;

import dto.CourierDto;
import dto.LoginDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import сlients.CourierClient;
import сlients.RestAssuredClient;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class CreateCourierDoubleLoginTest {
    private String firstName = "Max";
    private String password = "qwerty";
    private String login = "MaxiM141";
    private String password2 = "123";
    private String firstName2 = "Ivan";
    private static CourierClient courierClient;
    private int courierId;
    public static CourierDto courierDto1;
    public static CourierDto courierDto2;

    @Before
    public void setUp() {
        courierClient = new CourierClient(new RestAssuredClient());
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Невозможно создать курьера с логином, который есть")
    public void createCourierDoubleLoginError() {
        courierDto1 = new CourierDto(login, password, firstName);
        Response responseCreate1 = courierClient.create(courierDto1);
        courierDto2 = new CourierDto(login, password2, firstName2);
        Response responseCreate2 = courierClient.create(courierDto2);
        assertEquals(SC_CONFLICT, responseCreate2.statusCode());
        LoginDto loginDto = new LoginDto(courierDto1.getLogin(), courierDto1.getPassword());
        courierId = courierClient.login(loginDto)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }
}
