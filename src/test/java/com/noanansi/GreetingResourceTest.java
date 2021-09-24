package com.noanansi;

import io.quarkus.test.junit.QuarkusTest;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

  @Test
  public void givenNoPathVariable_whenGettingResource_thenOk() {
    given()
        .when().get("/hello")
        .then()
        .statusCode(200);
  }

  @Test
  public void givenNonExistingId_whenGettingResource_thenNotFound() {
    given()
        .when().get("/hello/" + UUID.randomUUID())
        .then()
        .statusCode(404);
  }

}