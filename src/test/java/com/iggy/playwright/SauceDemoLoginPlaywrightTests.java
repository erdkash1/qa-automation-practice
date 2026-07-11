package com.iggy.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoLoginPlaywrightTests {

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
        page.navigate("https://www.saucedemo.com");
    }

    @AfterEach
    void closePage() {
        page.close();
    }

    @Test
    void shouldLoginWithValidCredentials() {
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        assertTrue(page.url().contains("inventory"));
    }
}