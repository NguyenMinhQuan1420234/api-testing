package test.account;


import io.restassured.response.Response;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AccountTesting {
    @Test
    void GenToken() {
        RestAssured.baseURI = "https://demoqa.com";
        RequestSpecification request = RestAssured.given()
                .accept("application/json")
                .contentType("application/json");
        JSONObject requestParams = new JSONObject();
        requestParams.put("userName", "quan");
        requestParams.put("password", "@Abcd1234");
        request.body(requestParams.toJSONString());
        Response response = request.post("/Account/v1/GenerateToken");
        String token = response.jsonPath().getString("token");
        System.out.println(token);
        assertThat("verify message:",token,is(notNullValue()));

    }
}
