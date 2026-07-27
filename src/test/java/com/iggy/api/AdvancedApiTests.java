package com.iggy.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdvancedApiTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    @Order(1)
    void shouldRespondWithin3Seconds() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .time(lessThan(3000L));
    }

    @Test
    @Order(2)
    void shouldReturnJsonContentType() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    @Order(3)
    void shouldReturnCorrectPostFields() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test
    @Order(4)
    void shouldReturn100Posts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", equalTo(100));
    }

    @Test
    @Order(5)
    void shouldFilterPostsByUserId() {
        given()
                .queryParam("userId", 1)
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("size()", equalTo(10))
                .body("userId", everyItem(equalTo(1)));
    }

    @Test
    @Order(6)
    void shouldReturn404ForNonExistentPost() {
        given()
                .when()
                .get("/posts/99999")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(7)
    void shouldExtractAndVerifyResponseValue() {
        Response response = given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        int userId = response.path("userId");
        String title = response.path("title");

        System.out.println("✅ userId: " + userId);
        System.out.println("✅ title: " + title);

        assertTrue(userId > 0, "userId should be positive");
        assertTrue(title != null && !title.isEmpty(), "title should not be empty");
    }

    @Test
    @Order(8)
    void shouldCreateAndVerifyNewPost() {
        String requestBody = """
            {
                "title": "QA Automation Test Post",
                "body": "Created by RestAssured",
                "userId": 1
            }
            """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("QA Automation Test Post"))
                .body("userId", equalTo(1))
                .body("id", notNullValue());
    }
}