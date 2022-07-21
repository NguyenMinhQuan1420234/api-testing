package utils.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.APIConstant;

import java.util.HashMap;
import java.util.Map;

public class RequestHelper {

    public Response sendRequest(APIConstant.RequestType method, String url, Headers headers, Object body) {
        RestAssured.baseURI = "https://demoqa.com";
        if (headers == null) {
            Map<String,String> map = new HashMap<>();
            headers = createHeaders(map);
        }
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .header(headers);
        Response response = getResponse(request, method, url, body);

        return response;
    }


    private Response getResponse(RequestSpecification reqSpec, APIConstant.RequestType method, String url, Object body) {
        Response response;
        System.getProperty("com.sun.security.enableAIAcaIssuers", "true");
        switch (method) {
            case POST:
                if (null == body || body.toString().isEmpty()) {
                    response = reqSpec.when().post(url);
                } else {
                    response = reqSpec.body(body.toString()).when().post(url);
                }
                break;
            case PUT:
                response = reqSpec.body(body).when().put(url);
                break;
            case PATCH:
                response = reqSpec.body(body).when().patch(url);
            case DELETE:
                response = reqSpec.when().delete(url);
                break;
        }
    }
}
