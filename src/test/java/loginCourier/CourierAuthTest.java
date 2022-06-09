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

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class CourierAuthTest {
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
    @DisplayName("Провернка успешной авторизации")
    public void courierCanAuth() {
        LoginDto loginDto = new LoginDto(courierDto.getLogin(), courierDto.getPassword());
        Response responseLogin = courierClient.login(loginDto);
        assertEquals(SC_OK, responseLogin.statusCode());
        courierId = responseLogin.path("id");
    }

    @Test
    @DisplayName("Провернка неуспешной авторизации с неправильным логином")
    public void wrongLogin() {
        LoginDto loginDto = new LoginDto(courierDto.getLogin() + 1, courierDto.getPassword());
        Response responseLogin = courierClient.login(loginDto);
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
    }

    @Test
    @DisplayName("Провернка неуспешной авторизации с неправильным паролем")
    public void wrongPass() {
        LoginDto loginDto = new LoginDto(courierDto.getLogin(), courierDto.getPassword() + 1);
        Response responseLogin = courierClient.login(loginDto);
        assertEquals(SC_NOT_FOUND, responseLogin.statusCode());
    }

    @Test
    @DisplayName("Проверка, что успешный запрос возвращает id")
    public void idNotNull() {
        LoginDto loginDto = new LoginDto(courierDto.getLogin(), courierDto.getPassword());
        courierId = courierClient.login(loginDto)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");
        assertThat(courierId, notNullValue());
    }
}
