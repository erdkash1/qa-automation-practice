package com.iggy.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaywrightNetworkTests {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
    }

    @AfterAll
    static void closeBrowser() {
        playwright.close();
    }

    @BeforeEach
    void createPage() {
        page = browser.newPage();
    }

    @AfterEach
    void closePage() {
        page.close();
    }

    @Test
    void shouldInterceptAndVerifyApiCall() {
        boolean[] apiCalled = {false};

        page.route("**/jsonplaceholder.typicode.com/**", route -> {
            apiCalled[0] = true;
            route.resume();
        });

        page.navigate("https://jsonplaceholder.typicode.com/posts/1");
        assertTrue(apiCalled[0], "API should have been called");
    }

    @Test
    void shouldMockApiResponse() {
        page.route("**/posts/1", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setContentType("application/json")
                    .setBody("{\"id\":1,\"title\":\"Mocked Title\",\"userId\":99}")
            );
        });

        page.navigate("https://jsonplaceholder.typicode.com/posts/1");
        assertTrue(page.content().contains("Mocked Title"));
        assertTrue(page.content().contains("99"));
    }

    @Test
    void shouldBlockImages() {
        page.route("**/*.{png,jpg,jpeg,gif,svg}", route -> {
            route.abort();
        });

        page.navigate("https://www.saucedemo.com");
        assertThat(page.locator("[data-test='username']")).isVisible();
    }
}