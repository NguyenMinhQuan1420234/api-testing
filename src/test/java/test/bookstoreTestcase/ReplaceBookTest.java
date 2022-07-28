package test.bookstoreTestcase;

import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.APIConstant;
import utils.api.AccountHelper;
import utils.api.BookstoreHelper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ReplaceBookTest {
    AccountHelper accountHelper = new AccountHelper();
    BookstoreHelper bookstoreHelper = new BookstoreHelper();

    @BeforeTest
    public void getUser(ITestContext context) {
        String userToken = accountHelper.generateTokenString(APIConstant.PUBLIC_ACCOUNT_USER_NAME, APIConstant.PUBLIC_ACCOUNT_PASSWORD);
        String userId = APIConstant.PUBLIC_ACCOUNT_USER_ID;
        context.setAttribute("userToken", userToken);
        context.setAttribute("userId", userId);
    }

    @BeforeMethod
    public void addBookToCollection(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");

        bookstoreHelper.addNewBooks(userToken, userId, APIConstant.PUBLIC_BOOK_ID);
    }

    @Test
    public void replaceBookInCollectionSuccessfully(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");
        Response response = bookstoreHelper.replaceBook(userToken, userId, APIConstant.PUBLIC_BOOK_ID[0], APIConstant.NEW_BOOK_ID);

        assertThat("verify message: ", response.getStatusCode(), equalTo(200));
        bookstoreHelper.verifySchema(response,"bookDetail.json");
    }

    @Test
    public void replaceBookInCollectionUnsuccessfullyUnauthorized(ITestContext context) {
        String userToken = "fake Token";
        String userId = (String) context.getAttribute("userId");
        Response response = bookstoreHelper.replaceBook(userToken, userId, APIConstant.PUBLIC_BOOK_ID[0], APIConstant.NEW_BOOK_ID);

        assertThat("verify message: ", response.getStatusCode(), equalTo(401));
        assertThat("verify message: ", response.jsonPath().getString("message"),equalTo("User not authorized!"));
    }

    @Test
    public void replaceBookInCollectionUnsuccessfullyUnexistingIsbn(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");
        Response response = bookstoreHelper.replaceBook(userToken, userId, APIConstant.PUBLIC_BOOK_ID[0], "fake new isbn");

        assertThat("verify message: ", response.getStatusCode(), equalTo(400));
        assertThat("verify message: ", response.jsonPath().getString("message"),equalTo("ISBN supplied is not available in Books Collection!"));
    }

    @AfterMethod
    public void deleteBookAfterReplace(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");

        bookstoreHelper.deleteAllBooks(userToken,userId);
    }
}
