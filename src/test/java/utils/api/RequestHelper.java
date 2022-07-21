package utils.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.APIConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                .headers(headers);
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
            default:
                response = reqSpec.get(url);
                break;
        }
        System.out.println(String.format("URL: %s\n", url));
        System.out.println(String.format("Method: %s\n", method));
        System.out.println(String.format("The request is sent with: %s\n", body.toString()));
        System.out.println(String.format("The response is return with \nStatus code: %s \nResponse Body: ", response.statusCode()));

        response.prettyPrint();
        return response;
    }

    public static Headers getHeaders() {
        return  new Headers(new Header("Content-Type", "application/json"));
    }

    public static Headers createHeaders(Map<String, String> headersToAdd) {
        List<Header> headerList = new ArrayList<>();
        Headers requestHeaders = getHeaders();
        if (requestHeaders.exist()) {
            for (Header requestHeader : requestHeaders) {
                headerList.add(requestHeader);
            }
        }
        for (Map.Entry<String, String> stringEntry : headersToAdd.entrySet()) {
            headerList.add(new Header(stringEntry.getKey(), stringEntry.getValue()));
        }
        return new Headers(headerList);
    }

    public void verifySchema(Response response, String path) {
        JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchemaInClasspath(path);
        response.then().assertThat().body(validator);
    }

}
