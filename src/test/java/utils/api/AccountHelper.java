package utils.api;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONArray;
import utils.APIConstant;

import java.util.HashMap;
import java.util.Map;

public class AccountHelper extends RequestHelper {
    private String prefixUrl = APIConstant.DEMOQA_HOST + APIConstant.DEMOQA_ACCOUNT_PREFIX;

    public Response getUser(String userToken, String userId) {
        String url = prefixUrl + String.format(APIConstant.GET_DETAIL_USER_ENDPOINT, userId);
        Map<String,String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + userToken);
        Headers headers = createHeaders(map);
        Response response = sendRequest(
                APIConstant.RequestType.GET,
                url,
                headers, "");
        return response;
    }
    public Response createUser(String userName, String password) {
        String url = prefixUrl + APIConstant.CREATE_USER_ENDPOINT;
        JSONObject body = new JSONObject();
        body.put("userName", userName);
        body.put("password", password);
        Response response = sendRequest(
                APIConstant.RequestType.POST,
                url,
                null,
                body.toString());
        return response;
    }

    public Response generateToken(String userName, String password) {
        String url = prefixUrl + APIConstant.GENERATE_TOKEN_ENDPOINT;
        JSONObject body = new JSONObject();
        body.put("userName", userName);
        body.put("password", password);
        Map<String,String> map = new HashMap<>();
        Headers headers = createHeaders(map);
        Response response = sendRequest(
                APIConstant.RequestType.POST,
                url,
                headers,
                body.toString());
        String token = response.jsonPath().getString("token");
        return response;
    }

    public String generateTokenString(String userName, String password) {
        String url = prefixUrl + APIConstant.GENERATE_TOKEN_ENDPOINT;
        JSONObject body = new JSONObject();
        body.put("userName", userName);
        body.put("password", password);
        Map<String,String> map = new HashMap<>();
        Headers headers = createHeaders(map);
        Response response = sendRequest(
                APIConstant.RequestType.POST,
                url,
                headers,
                body.toString());
        return response.jsonPath().getString("token");
    }
}
