package test.accountTestcase;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.APIConstant;
import utils.api.AccountHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetUserTest {
    AccountHelper accountHelper = new AccountHelper();

    @Test
    void getUserSuccessfully() {
        String token = accountHelper.generateTokenString(APIConstant.PUBLIC_ACCOUNT_USER_NAME,APIConstant.PUBLIC_ACCOUNT_PASSWORD);
        String userId = APIConstant.PUBLIC_ACCOUNT_USER_ID;

        Response response = accountHelper.getUser(token,userId);

        assertThat("verify message: ", response.getStatusCode(), equalTo(200));
    }

    @Test
    void getUserUnsuccessfully() {
        String token = accountHelper.generateTokenString(APIConstant.PUBLIC_ACCOUNT_USER_NAME,APIConstant.PUBLIC_ACCOUNT_PASSWORD);
        String userId = "invalid userID";

        Response response = accountHelper.getUser(token,userId);

        assertThat("verify message: ", response.getStatusCode(), equalTo(401));
    }


}
