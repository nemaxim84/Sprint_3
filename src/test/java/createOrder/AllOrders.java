package createOrder;

import dto.OrderDto;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import сlients.OrderClient;
import сlients.RestAssuredClient;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;

public class AllOrders {
    private static OrderClient orderClient;
    public static OrderDto orderDto;

    @Before
    public void setUp() {
        orderClient = new OrderClient(new RestAssuredClient());
        orderDto = new OrderDto();
    }

    @Test
    public void viewAllOrdersTest() {
        Response responseCreate = orderClient.gettingList();
        assertEquals(SC_OK, responseCreate.statusCode());
        assertThat(responseCreate.path("orders"), notNullValue());
    }
}
