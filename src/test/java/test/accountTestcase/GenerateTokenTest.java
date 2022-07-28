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

public class GenerateTokenTest {

    AccountHelper accountHelper = new AccountHelper();

    @Test
    void generateTokenSuccessfully() {
        String username = APIConstant.PUBLIC_ACCOUNT_USER_NAME;
        String password = APIConstant.PUBLIC_ACCOUNT_PASSWORD;
        Response response = accountHelper.generateToken(username, password);

        String token = response.jsonPath().getString("token");
        assertThat("verify message: ", response.getStatusCode(), equalTo(200));
        assertThat("verify message: ", token, is(notNullValue()));
        assertThat(response.jsonPath().getString("result"),equalTo("User authorized successfully."));

    }

    @Test
    void generateTokenUnsuccessfullyUnauthorized() {
        String username = "invalid username";
        String password = APIConstant.PUBLIC_ACCOUNT_PASSWORD;
        Response response = accountHelper.generateToken(username, password);

        assertThat("verify message: ", response.getStatusCode(), equalTo(200));
        assertThat(response.jsonPath().getString("result"),equalTo("User authorization failed."));
    }

    @Test
    void generateTokenUnsuccessfullyEmptyPassword() {
        String username = "invalid username";
        String password = "";
        Response response = accountHelper.generateToken(username, password);

        assertThat("verify message: ", response.getStatusCode(), equalTo(400));
        assertThat(response.jsonPath().getString("message"),equalTo("UserName and Password required."));
    }
}
