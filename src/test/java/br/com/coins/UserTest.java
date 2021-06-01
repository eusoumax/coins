package br.com.coins;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

import javax.ws.rs.WebApplicationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.coinone.model.User;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class UserTest {

    @Test
    private void testUSerEndpoint() {
        given()
        .when().get("/user")
        .then()
        .statusCode(200)
        .body(
                containsString("Alan da Silva"),
                containsString("Carlos da Silva"),
                containsString("Luiz Coutinho")
        );
    }
    
    @Test
    private void UserMockTest() {
            PanacheMock.mock(User.class);
            
            
            
            // Mock only with specific parameters
            User p = new User();
            Mockito.when(User.findById(500L)).thenReturn(p);
            Assertions.assertSame(p, User.findById(500L));
            Assertions.assertNull(User.findById(42L));
            
            
            // Mock throwing
            Mockito.when(User.findById(500L)).thenThrow(new WebApplicationException());
            Assertions.assertThrows(WebApplicationException.class, () -> User.findById(500L));
    }


}