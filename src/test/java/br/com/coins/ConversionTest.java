package br.com.coins;

import org.junit.jupiter.api.Test;

import br.com.coinone.dto.OperationDTO;

import static io.restassured.RestAssured.given;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ConversionTest {


    @Test
    private void testUSerEndpoint() {
        given()
        .contentType("application/json")
        .body(new OperationDTO())
        .when()
        .post("/makeConversion/good")
        .then()
        .statusCode(400);
    }
    
}
