package com.iggy.playwright;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlaywrightApiTests {

    private static Playwright playwright;
    private static APIRequestContext request;

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        request = playwright.request().newContext(
                new APIRequest.NewContextOptions()
                        .setBaseURL("https://jsonplaceholder.typicode.com")
        );
    }

    @AfterAll
    static void tearDown() {
        request.dispose();
        playwright.close();
    }

    @Test
    void shouldGetPostById() {
        APIResponse response = request.get("/posts/1");

        assertEquals(200, response.status());

        JsonObject body = JsonParser.parseString(response.text()).getAsJsonObject();
        assertEquals(1, body.get("id").getAsInt());
        assertEquals(1, body.get("userId").getAsInt());
        assertNotNull(body.get("title").getAsString());

        System.out.println("Title: " + body.get("title").getAsString());
    }

    @Test
    void shouldGetAllPosts() {
        APIResponse response = request.get("/posts");

        assertEquals(200, response.status());

        JsonArray posts = JsonParser.parseString(response.text()).getAsJsonArray();
        assertEquals(100, posts.size());

        System.out.println("Total posts: " + posts.size());
    }

    @Test
    void shouldCreatePost() {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("title", "Playwright API Test");
        requestBody.addProperty("body", "Testing with Playwright");
        requestBody.addProperty("userId", 1);

        APIResponse response = request.post("/posts",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(requestBody.toString())
        );

        assertEquals(201, response.status());

        JsonObject body = JsonParser.parseString(response.text()).getAsJsonObject();
        assertEquals("Playwright API Test", body.get("title").getAsString());
        assertNotNull(body.get("id"));

        System.out.println("Created post ID: " + body.get("id").getAsInt());
    }

    @Test
    void shouldUpdatePost() {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("id", 1);
        requestBody.addProperty("title", "Updated Title");
        requestBody.addProperty("body", "Updated body");
        requestBody.addProperty("userId", 1);

        APIResponse response = request.put("/posts/1",
                RequestOptions.create()
                        .setHeader("Content-Type", "application/json")
                        .setData(requestBody.toString())
        );

        assertEquals(200, response.status());

        JsonObject body = JsonParser.parseString(response.text()).getAsJsonObject();
        assertEquals("Updated Title", body.get("title").getAsString());

        System.out.println("Updated title: " + body.get("title").getAsString());
    }

    @Test
    void shouldDeletePost() {
        APIResponse response = request.delete("/posts/1");
        assertEquals(200, response.status());
        System.out.println("Post deleted successfully");
    }

    @Test
    void shouldReturn404ForNonExistentPost() {
        APIResponse response = request.get("/posts/99999");
        assertEquals(404, response.status());
        System.out.println("404 returned for non-existent post");
    }

    @Test
    void shouldFilterPostsByUserId() {
        APIResponse response = request.get("/posts",
                RequestOptions.create()
                        .setQueryParam("userId", "1")
        );

        assertEquals(200, response.status());

        JsonArray posts = JsonParser.parseString(response.text()).getAsJsonArray();
        assertEquals(10, posts.size());

        posts.forEach(post -> {
            assertEquals(1, post.getAsJsonObject().get("userId").getAsInt());
        });

        System.out.println("Filtered " + posts.size() + " posts for userId 1");
    }
}