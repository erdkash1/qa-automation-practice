package com.iggy.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ProductApiTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    void shouldReturn200ForPostsEndpoint() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200);
    }

    @Test
    void shouldReturn100Posts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", equalTo(100));
    }

    @Test
    void shouldReturnFirstPostWithCorrectTitle() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1));
    }
    @Test
    void shouldCreateNewPost() {
        given()
                .contentType("application/json")
                .body("{\"title\":\"test post\",\"body\":\"test body\",\"userId\":1}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("test post"))
                .body("id", notNullValue());
    }

    @Test
    void shouldUpdatePost() {
        given()
                .contentType("application/json")
                .body("{\"id\":1,\"title\":\"updated title\",\"body\":\"updated body\",\"userId\":1}")
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("updated title"));
    }

    @Test
    void shouldDeletePost() {
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }
}