package utils.api;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONArray;
import utils.APIConstant;

import java.util.HashMap;
import java.util.Map;

public class BookstoreHelper extends RequestHelper{
    private String prefixUrl = APIConstant.DEMOQA_HOST + APIConstant.DEMOQA_BOOK_STORE_PREFIX;

    public Response addNewBook(String userToken, String userId, String[] isbn) {
        String url = prefixUrl + APIConstant.ADD_DELETE_BOOK_TO_COLLECTION_ENDPOINT;
        Map<String,String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + userToken);
        Headers headers = createHeaders(map);
        JSONArray collectionOfIsbns = new JSONArray();
        for (String id: isbn) {
            JSONObject bookId = new JSONObject();
            bookId.put("isbn", id);
            collectionOfIsbns.put(bookId);
        }
        JSONObject body = new JSONObject();
        body.put("userID", userId);
        body.put("collectionOfIsbns", collectionOfIsbns);
        Response response = sendRequest(
                APIConstant.RequestType.POST,
                url,
                headers,
                body.toString());
        return response;
    }

    public Response deleteAllBooks(String userToken, String userId) {
        String url = prefixUrl + String.format(APIConstant.DELETE_ALL_BOOKS_IN_COLLECTION_ENDPOINT, userId);
        Map<String,String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + userToken);
        Headers headers = createHeaders(map);
        Response response = sendRequest(
                APIConstant.RequestType.DELETE,
                url,
                headers,
                "");
        return response;
    }

    public Response deleteBook(String userToken, String userId, String isbn) {
        String url = prefixUrl + String.format(APIConstant.DELETE_ALL_BOOKS_IN_COLLECTION_ENDPOINT, userId);
        Map<String,String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + userToken);
        Headers headers = createHeaders(map);
        JSONObject body = new JSONObject();
        body.put("íbsn", isbn);
        body.put("userId", userId);
        Response response = sendRequest(
                APIConstant.RequestType.DELETE,
                url,
                headers,
                body.toString());
        return response;
    }

    public Response replaceBook(String userToken, String userId, String currentIsbn, String newIsbn) {
        String url = prefixUrl + String.format(APIConstant.REPLACE_BOOK_ENDPOINT, currentIsbn);
        Map<String,String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + userToken);
        Headers headers = createHeaders(map);
        JSONObject body = new JSONObject();
        body.put("íbsn", newIsbn);
        body.put("userId", userId);
        Response response = sendRequest(
                APIConstant.RequestType.PUT,
                url,
                headers,
                body.toString());
        return response;
    }
}
