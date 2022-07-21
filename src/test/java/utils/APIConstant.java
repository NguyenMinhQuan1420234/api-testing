package utils;

public class APIConstant {
    public enum RequestType {
        GET,
        POST,
        PUT,
        DELETE,
        PATCH
    }

    public static String DEMOQA_HOST = "https://demoqa.com";
    public static String PUBLIC_ACCOUNT_USER_NAME = "quan";
    public static String PUBLIC_ACCOUNT_PASSWORD = "@Abcd1234";
    public static String PUBLIC_ACCOUNT_USER_ID = "9e52e702-df09-4124-96a1-b99f1087eff9";
    public static String PUBLIC_ACCOUNT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6InF1YW4iLCJwYXNzd29yZCI6Ik" +
            "BBYmNkMTIzNCIsImlhdCI6MTY1ODQyNTEwNX0.cpqcC58P-kb14ngskeeO1rAjGnu5cOk7wF2_Hj93odw";
    public static String[] PUBLIC_BOOK_ID = {"9781449325862", "9781449331818"};
    public static String DEMOQA_ACCOUNT_PREFIX = "/Account/v1";
    public static String LOGIN_ENDPOINT = "/Authorized";
    public static String GENERATE_TOKEN_ENDPOINT = "/GenerateToken";
    public static String CREATE_USER_ENDPOINT = "/User";
    public static String GET_DETAIL_USER_ENDPOINT = "/User";
    public static String DEMOQA_BOOK_STORE_PREFIX = "/BookStore/v1";
    public static String REPLACE_BOOK_ENDPOINT = "/Books/%s";
    public static String ADD_DELETE_BOOK_TO_COLLECTION_ENDPOINT = "/Books";
    public static String DELETE_ALL_BOOKS_IN_COLLECTION_ENDPOINT = "/Books?UserId=%s";
}
