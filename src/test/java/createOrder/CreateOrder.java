package createOrder;

import dto.OrderDto;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import сlients.OrderClient;
import сlients.RestAssuredClient;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class CreateOrder {
    private static OrderClient orderClient;
    public static OrderDto orderDto;

    @Parameterized.Parameter
    public String[] color;

    @Parameterized.Parameters
    public static String[][][] params() throws Exception {
        return new String[][][]{
                {{"BLACK", "GREY"}},
                {{"BLACK"}},
                {{"GREY"}},
                {null}
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient(new RestAssuredClient());
        orderDto = new OrderDto();
        orderDto.setFirstName("Naruto2");
        orderDto.setLastName("Uchiha");
        orderDto.setAddress("Konoha, 142 apt.");
        orderDto.setMetroStation("4");
        orderDto.setPhone("+7 800 355 35 35");
        orderDto.setRentTime(5);
        orderDto.setDeliveryDate("2020-06-06");
        orderDto.setComment("Saske, come back to Konoha");
        orderDto.setColor(color);
    }

    @Test
    @DisplayName("Тест по созданию заказа с цветом {color}")
    public void createOrdersWithParams() {
        Response responseCreate = orderClient.create(orderDto);
        assertEquals(SC_CREATED, responseCreate.statusCode());
        assertThat(responseCreate.path("track"), notNullValue());
    }
}
