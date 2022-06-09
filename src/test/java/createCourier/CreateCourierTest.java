package createCourier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import сlients.CourierClient;
import сlients.RestAssuredClient;
import dto.CourierDto;
import dto.LoginDto;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {
    private String firstName = "Max";
    private String password = "qwerty";
    private String login = "MaxiM777777";
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
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Создание курьера со всеми полями и проверка, что он создался")
    public void createCourierValid() {
        boolean isCreated = courierClient.create(courierDto)
                .then().statusCode(SC_CREATED)
                .extract()
                .path("ok");
        assertTrue(isCreated);
        LoginDto loginDto = new LoginDto(courierDto.getLogin(), courierDto.getPassword());
        courierId = courierClient.login(loginDto)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Нельзя создать даух одинаоквых курьеров")
    public void twoIdenticalCourierError() {
        Response responseCreate1 = courierClient.create(courierDto);
        Response responseCreate2 = courierClient.create(courierDto);
        assertEquals(SC_CONFLICT, responseCreate2.statusCode());
        LoginDto loginDto = new LoginDto(courierDto.getLogin(), courierDto.getPassword());
        courierId = courierClient.login(loginDto)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }

    @Test
    @DisplayName("Проверка статус кода")
    public void checkStatusCode() {
        Response responseCreate = courierClient.create(courierDto);
        assertEquals(SC_CREATED, responseCreate.statusCode());
        LoginDto loginDto = new LoginDto(courierDto.getLogin(), courierDto.getPassword());
        courierId = courierClient.login(loginDto)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");
    }

}
