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

public class DeleteBookTest {
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
    public void deleteAddedBookInCollectionSuccessfully(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");
        bookstoreHelper.addNewBooks(userToken,userId,APIConstant.PUBLIC_BOOK_ID[1]);
        Response response = bookstoreHelper.deleteBook(userToken, userId, APIConstant.PUBLIC_BOOK_ID[1]);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(204));
    }

    @Test
    public void deleteAddedBookInCollectionUnsuccessfullyWrongToken(ITestContext context) {
        String userToken = "fake Token";
        String userId = (String) context.getAttribute("userId");
        Response response = bookstoreHelper.deleteBook(userToken, userId, APIConstant.PUBLIC_BOOK_ID[1]);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(401));
        assertThat("verify message: ", response.jsonPath().getString("message"),equalTo("User not authorized!"));
    }

    @Test
    public void deleteAddedBookInCollectionUnsuccessfullyUnexistingIsbn(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");
        Response response = bookstoreHelper.deleteBook(userToken, userId, "fake isbn");

        assertThat("verify status code: ", response.getStatusCode(), equalTo(400));
        assertThat("verify message: ", response.jsonPath().getString("message"),equalTo("ISBN supplied is not available in User's Collection!"));
    }



    @Test
    public void deleteAllBooksInCollectionSuccessfully(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");
        bookstoreHelper.addNewBooks(userToken, userId, APIConstant.PUBLIC_BOOK_ID);
        Response response = bookstoreHelper.deleteAllBooks(userToken, userId);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(204));
    }

    @Test
    public void deleteAllBooksInCollectionUnsuccessfullyUnauthorized(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = "fake userid";
        Response response = bookstoreHelper.deleteAllBooks(userToken, userId);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(401));
        assertThat("verify message: ", response.jsonPath().getString("message"),equalTo("User Id not correct!"));
    }

}
