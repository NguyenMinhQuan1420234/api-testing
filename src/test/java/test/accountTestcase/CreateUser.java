package test.accountTestcase;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.APIConstant;
import utils.api.AccountHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateUser {
    AccountHelper accountHelper = new AccountHelper();

    @Test
    void CreateUserSuccessfully() {
        String username = "quantest";
        String password = APIConstant.PUBLIC_ACCOUNT_PASSWORD;

        Response response = accountHelper.createUser(username,password);
        assertThat("verify message: ", response.getStatusCode(), equalTo(201));
    }

    @Test
    void CreateUserUnsuccessfully() {
        String username = "quantest";
        String password = "12345678";

        Response response = accountHelper.createUser(username,password);
        assertThat("verify message: ", response.getStatusCode(), equalTo(400));
    }

}
