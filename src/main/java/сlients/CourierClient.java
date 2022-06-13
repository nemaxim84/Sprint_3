package сlients;

import dto.CourierDto;
import dto.LoginDto;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.SC_OK;

public class CourierClient {
    private final RestAssuredClient restAssuredClient;

    public CourierClient(RestAssuredClient restAssuredClient) {
        this.restAssuredClient = restAssuredClient;
    }

    public Response login(LoginDto loginDto) {
        return restAssuredClient.post("courier/login", loginDto);
    }
    public int loginValidID (LoginDto loginDto, int exceptedStatusCode){
        return restAssuredClient.post("courier/login", loginDto).then()
                .statusCode(exceptedStatusCode)
                .extract()
                .path("id");
    }

    public Response create(CourierDto courierDto) {
        return restAssuredClient.post("courier", courierDto);
    }

    public boolean delete(int courierId) {
        return restAssuredClient.delete("courier/{courierId}", courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }
}
