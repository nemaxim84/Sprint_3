package createCourier;

import dto.CourierDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import сlients.CourierClient;
import сlients.RestAssuredClient;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;

public class CreateWithoutParamsTest {
    //тестовые данные
    private String firstName = "Max";
    private String password = "qwerty";
    private String login = "MaxiM134";
    String expectedMessage = "Недостаточно данных для создания учетной записи";
    private static CourierClient courierClient;
    public static CourierDto courierDto;

    @Before
    public void setUp() {
        courierClient = new CourierClient(new RestAssuredClient());
        courierDto = new CourierDto();
    }

    @Test
    @DisplayName("Создание курьера без логина")
    public void createWithoutLogin() {
        CourierDto courierDto = new CourierDto();
        courierDto.setFirstName(firstName);
        courierDto.setPassword(password);
        Response responseCreate = courierClient.create(courierDto);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        System.out.println((String) responseCreate.path("message"));
        assertEquals((String) responseCreate.path("message"), expectedMessage);
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    public void createWithoutPassword() {
        CourierDto courierDto = new CourierDto();
        courierDto.setFirstName(firstName);
        courierDto.setLogin(login);
        Response responseCreate = courierClient.create(courierDto);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        assertEquals((String) responseCreate.path("message"), expectedMessage);
    }
    @Test
    @DisplayName("Создание курьера без имени")
    public void createWithoutName() {
        CourierDto courierDto = new CourierDto();
        courierDto.setLogin(login);
        courierDto.setPassword(password);
        Response responseCreate = courierClient.create(courierDto);
        assertEquals(SC_BAD_REQUEST, responseCreate.statusCode());
        assertEquals((String) responseCreate.path("message"), expectedMessage);
    }
}
