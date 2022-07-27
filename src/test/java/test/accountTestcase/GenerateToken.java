package test.accountTestcase;


import io.restassured.response.Response;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import utils.APIConstant;
import utils.api.AccountHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GenerateToken {

    AccountHelper accountHelper = new AccountHelper();
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

    @Test
    void GenerateTokenSuccessfully() {
        String username = APIConstant.PUBLIC_ACCOUNT_USER_NAME;
        String password = APIConstant.PUBLIC_ACCOUNT_PASSWORD;

        Response response = accountHelper.generateToken(username, password);
        String token = response.jsonPath().getString("token");
        assertThat("verify message: ", token, is(notNullValue()));
    }

    @Test
    void GenerateTokenUnsuccessfully() {
        String username = "invalid username";
        String password = APIConstant.PUBLIC_ACCOUNT_PASSWORD;

        Response response = accountHelper.generateToken(username, password);
//        assertThat("verify message: ", response.jsonPath().getString("token"), is(null));
        assertThat(response.jsonPath().getString("result"),equalTo("User authorization failed."));
    }


}
