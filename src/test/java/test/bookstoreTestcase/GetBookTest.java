package test.bookstoreTestcase;

import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.APIConstant;
import utils.api.AccountHelper;
import utils.api.BookstoreHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetBookTest {
    AccountHelper accountHelper = new AccountHelper();
    BookstoreHelper bookstoreHelper = new BookstoreHelper();

    @BeforeTest
    public void getUser(ITestContext context) {
        String userToken = accountHelper.generateTokenString(APIConstant.PUBLIC_ACCOUNT_USER_NAME, APIConstant.PUBLIC_ACCOUNT_PASSWORD);
        String userId = APIConstant.PUBLIC_ACCOUNT_USER_ID;
        context.setAttribute("userToken", userToken);
        context.setAttribute("userId", userId);
    }

    @Test
    public void getAllBookInformationSuccessfully() {
        String isbn = APIConstant.PUBLIC_BOOK_ID[0];
        Response response = bookstoreHelper.getAllBookInfo(isbn);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(200));
    }

    @Test
    public void getOneBookInformationSuccessfully() {
        String isbn = APIConstant.PUBLIC_BOOK_ID[0];
        Response response = bookstoreHelper.getOneBookInfo(isbn);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(200));
    }

    @Test
    public void getOneBookInformationUnsuccessfully() {
        String isbn = "no data";
        Response response = bookstoreHelper.getOneBookInfo(isbn);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(400));
        assertThat("verify message: ", response.jsonPath().getString("message"),equalTo("ISBN supplied is not available in Books Collection!"));
    }
}
