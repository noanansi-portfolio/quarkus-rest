package com.noanansi;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.junit.jupiter.api.Test;

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

  @Test
  public void givenEmptyBody_whenGettingResource_thenBadRequest() {
    given()
        .body("").contentType(ContentType.JSON)
        .post("/hello")
        .then()
        .statusCode(400);
  }

  @Test
  public void givenNullMessage_whenGettingResource_thenBadRequest() {
    given()
        .body("{}").contentType(ContentType.JSON)
        .post("/hello")
        .then()
        .statusCode(400);
  }

  @Test
  public void givenValidMessage_whenGettingResource_thenBadRequest() {
    given()
        .body("{\"message\":\"Hi there!\"}").contentType(ContentType.JSON)
        .post("/hello")
        .then()
        .statusCode(201);
  }

}