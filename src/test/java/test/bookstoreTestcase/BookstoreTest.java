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

public class BookstoreTest {
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
    public void getAllBookInformation() {
        String isbn = APIConstant.PUBLIC_BOOK_ID[0];
        Response response = bookstoreHelper.getAllBookInfo(isbn);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(200));
    }

    @Test
    public void getOneBookInformation() {
        String isbn = APIConstant.PUBLIC_BOOK_ID[0];
        Response response = bookstoreHelper.getOneBookInfo(isbn);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(200));
    }

    @Test
    public void addNewBookToCollectionSuccessfully(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");

        Response response = bookstoreHelper.addNewBook(userToken, userId, APIConstant.PUBLIC_BOOK_ID);

        assertThat("verify status code: ", response.getStatusCode(), equalTo(201));
    }

    @Test
    public void deleteAddedBookInCollectionSuccessfully(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");

        Response response = bookstoreHelper.deleteBook(userToken, userId, APIConstant.PUBLIC_BOOK_ID[1]);
        assertThat("verify status code: ", response.getStatusCode(), equalTo(204));
    }

    @Test
    public void deleteAllBooksInCollectionSuccessfully(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");

        Response response = bookstoreHelper.deleteAllBooks(userToken, userId);
        assertThat("verify status code: ", response.getStatusCode(), equalTo(204));
    }

    @Test
    public void replaceBookInCollectionSuccessfully(ITestContext context) {
        String userToken = (String) context.getAttribute("userToken");
        String userId = (String) context.getAttribute("userId");
        bookstoreHelper.addNewBook(userToken, userId, APIConstant.PUBLIC_BOOK_ID);
        Response response = bookstoreHelper.replaceBook(userToken, userId, APIConstant.PUBLIC_BOOK_ID[0], APIConstant.NEW_BOOK_ID);
        assertThat("verify message: ", response.getStatusCode(), equalTo(200));
        bookstoreHelper.deleteAllBooks(userToken,userId);
    }
}
