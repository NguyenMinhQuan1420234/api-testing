import org.testng.annotations.Test;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class apiTest {
    @Test
    public void verifyStatusCode() {
        given()
                .get("https://serverest.dev/usuarios")
                .then()
                .statusCode(200);
    }

    @Test
    public void verifyAttribute() {
        RestAssured.given().accept("https://serverest.dev/usuarios").then().body("quantidade",equalTo(4));
    }
}

