package test.bookstoreTestcase;

import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.APIConstant;
import utils.api.AccountHelper;
import utils.api.BookstoreHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AddBookTest {
    AccountHelper accountHelper = new AccountHelper();
    BookstoreHelper bookstoreHelper = new BookstoreHelper();

    @BeforeMethod
    public void getUser(ITestContext context) {
        String userToken = accountHelper.generateTokenString(APIConstant.PUBLIC_ACCOUNT_USER_NAME, APIConstant.PUBLIC_ACCOUNT_PASSWORD);
        String userId = APIConstant.PUBLIC_ACCOUNT_USER_ID;
        context.setAttribute("userToken", userToken);
        context.setAttribute("userId", userId);
    }

    @Test
    public void addNewBookToCollectionSuccessfully(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");
        Response response = bookstoreHelper.addNewBooks(userToken, userId, APIConstant.PUBLIC_BOOK_ID);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(201));

    }

    @Test
    public void addNewBookToCollectionUnsuccessfullyMissingIsbn(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");
        Response response = bookstoreHelper.addNewBooks(userToken, userId, APIConstant.INVALID_BOOK_ID);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(400));
        assertThat("verify message: ", response.jsonPath().getString("message"),equalTo("ISBN supplied is not available in Books Collection!"));
    }

    @Test
    public void addNewBookToCollectionUnsuccessfullyExistingBook(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");
        bookstoreHelper.addNewBooks(userToken, userId, APIConstant.PUBLIC_BOOK_ID[0]);
        Response response = bookstoreHelper.addNewBooks(userToken, userId, APIConstant.PUBLIC_BOOK_ID[0]);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(400));
        assertThat("verify message: ", response.jsonPath().getString("message"),equalTo("ISBN already present in the User's Collection!"));
    }

    @Test
    public void addNewBookToCollectionUnsuccessfullyIncorrectUser(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = "fake Id";
        Response response = bookstoreHelper.addNewBooks(userToken, userId, APIConstant.PUBLIC_BOOK_ID);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(401));
        assertThat("verify message: ", response.jsonPath().getString("message"),equalTo("User Id not correct!"));
    }

    @AfterMethod
    public void deleteBooksInCollection(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");

        Response response = bookstoreHelper.deleteAllBooks(userToken, userId);
    }

}
