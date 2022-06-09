package сlients;


import dto.OrderDto;
import io.restassured.response.Response;

public class OrderClient {
    private final RestAssuredClient restAssuredClient;

    public OrderClient(RestAssuredClient restAssuredClient) {
        this.restAssuredClient = restAssuredClient;
    }

    public Response gettingList() {
        return restAssuredClient.get("orders");
    }

    public Response create(OrderDto orderDto) {
        return restAssuredClient.post("orders", orderDto);
    }

}
