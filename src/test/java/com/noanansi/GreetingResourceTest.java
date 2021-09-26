package com.noanansi;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class GreetingResourceTest {

  @Test
  public void givenNoPathVariable_whenGettingResource_thenOkResponse() {
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
  public void givenValidMessage_whenGettingResource_thenCreatedResponse() {
    given()
        .body("{\"message\":\"Hi there!\"}").contentType(ContentType.JSON)
        .post("/hello")
        .then()
        .statusCode(201);
  }

  @Test
  public void givenExistingMessage_whenDeletingResource_thenOkResponse() {
    final var response = given()
        .body("{\"message\":\"Hi there!\"}").contentType(ContentType.JSON)
        .post("/hello");
    final var responseBody = response.body().as(Map.class);
    given()
        .delete("/hello/" + responseBody.get("id"))
        .then()
        .statusCode(200);
  }

  @Test
  public void givenExistingMessage_whenUpdatingResource_thenOkResponse() {
    final var creationResponse = given()
        .body("{\"message\":\"Hi there!\"}").contentType(ContentType.JSON)
        .post("/hello");
    final var creationResponseBody = creationResponse.body().as(Map.class);
    final var updatedMessage = "Hi there2!";
    final var id = creationResponseBody.get("id");
    final var updateResponseBody = given()
        .body("{\"message\":\"" + updatedMessage + "\"}").contentType(ContentType.JSON)
        .patch("/hello/" + id)
        .body().as(Map.class);
    Assertions.assertEquals(updatedMessage, updateResponseBody.get("message"));
    final var updatedResponse = given()
        .get("/hello/" + id).body().as(Map.class);
    Assertions.assertEquals(updatedMessage, updatedResponse.get("message"));
  }

}