package loginCourier;

import dto.CourierDto;
import dto.LoginDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import сlients.CourierClient;
import сlients.RestAssuredClient;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class LoginWithoutParamsTest {
    private String firstName = "Max";
    private String password = "qwerty";
    private String login = "MaxiM139";
    private static CourierClient courierClient;
    private int courierId;
    public static CourierDto courierDto;

    @Before
    public void setUp() {
        courierClient = new CourierClient(new RestAssuredClient());
        courierDto = new CourierDto();
        courierDto.setFirstName(firstName);
        courierDto.setLogin(login);
        courierDto.setPassword(password);
        courierClient.create(courierDto);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierClient.login(new LoginDto(login, password))
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id"));
    }

    @Test
    @DisplayName("Попытка залогиниться без пароля")
    public void LoginWithoutPass() {
        LoginDto loginDto = new LoginDto();
        loginDto.setLogin(courierDto.getLogin());
        Response responseLogin = courierClient.login(loginDto);
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
    }
    @Test
    @DisplayName("Попытка залогиниться без логина")
    public void LoginWithoutLogin() {
        LoginDto loginDto = new LoginDto();
        loginDto.setPassword(courierDto.getPassword());
        Response responseLogin = courierClient.login(loginDto);
        assertEquals(SC_BAD_REQUEST, responseLogin.statusCode());
    }
}
