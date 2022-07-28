package test.accountTestcase;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.APIConstant;
import utils.api.AccountHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {
    AccountHelper accountHelper = new AccountHelper();

    @Test
    void createUserSuccessfully() {
        String username = "quantest " + LocalDateTime.now();
        String password = APIConstant.PUBLIC_ACCOUNT_PASSWORD;

        Response response = accountHelper.createUser(username,password);

        assertThat("verify message: ", response.getStatusCode(), equalTo(201));
    }

    @Test
    void createUserUnsuccessfullyWrongPasswordFormat() {
        String username = "quantest";
        String password = "12345678";

        Response response = accountHelper.createUser(username,password);

        assertThat("verify message: ", response.getStatusCode(), equalTo(400));
        assertThat(response.jsonPath().getString("message"), equalTo("Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), " +
                "one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer."));
    }

    @Test
    void createUserUnsuccessfullyUserExisted() {
        String username = "quan";
        String password = "@Test1234";

        Response response = accountHelper.createUser(username,password);

        assertThat("verify message: ", response.getStatusCode(), equalTo(406));
        assertThat(response.jsonPath().getString("message"), equalTo("User exists!"));
    }
}
