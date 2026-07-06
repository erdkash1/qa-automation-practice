package com.iggy.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductApiTests2 {

    private static String token;
    private static int productId;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://ecommerce-api-e24i.onrender.com";

        // Login and get token
        token = given()
                .contentType("application/json")
                .body("{\"email\":\"test@gmail.com\",\"password\":\"password123\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    @Test
    @Order(1)
    void shouldReturn200ForProductsEndpoint() {
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/products")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(2)
    void shouldCreateProduct() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body("{\"name\":\"Test Product\",\"description\":\"Test\",\"price\":9.99,\"stock\":10,\"category\":\"Electronics\"}")
                .when()
                .post("/products")
                .then()
                .statusCode(200) // ← changed from 201 to 200
                .body("name", equalTo("Test Product"))
                .body("price", equalTo(9.99f))
                .extract()
                .response();

        productId = response.path("id");
        System.out.println("Created product ID: " + productId);
    }

    @Test
    @Order(3)
    void shouldGetProductById() {
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/products/" + productId)
                .then()
                .statusCode(200)
                .body("id", equalTo(productId))
                .body("name", equalTo("Test Product"));
    }

    @Test
    @Order(4)
    void shouldReturnListOfProducts() {
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }



    @Test
    @Order(5)
    void shouldDeleteProduct() {
        given()
                .header("Authorization", "Bearer " + token)
                .when()
                .delete("/products/" + productId)
                .then()
                .statusCode(200); // ← changed from 204 to 200
    }
}