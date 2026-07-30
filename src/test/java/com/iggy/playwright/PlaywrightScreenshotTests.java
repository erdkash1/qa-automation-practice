package com.iggy.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class PlaywrightScreenshotTests {

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
    void takeScreenshotOnFailure(TestInfo testInfo) {
        try {
            Files.createDirectories(Paths.get("playwright-screenshots"));
            String testName = testInfo.getDisplayName()
                    .replaceAll("[^a-zA-Z0-9_-]", "_");
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("playwright-screenshots/" + testName + ".png"))
                    .setFullPage(true));
            System.out.println("📸 Screenshot saved: " + testName + ".png");
        } catch (Exception e) {
            System.err.println("Screenshot failed: " + e.getMessage());
        }
        page.close();
    }

    @Test
    void shouldLoginAndTakeScreenshot() {
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        assertThat(page.locator("[data-test='title']")).hasText("Products");
    }

    @Test
    void shouldCaptureErrorScreenshot() {
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "wrong_user");
        page.fill("[data-test='password']", "wrong_pass");
        page.click("[data-test='login-button']");
        assertThat(page.locator("[data-test='error']"))
                .containsText("Epic sadface");
    }

    @Test
    void shouldCaptureCartScreenshot() {
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        page.click("[data-test='add-to-cart-sauce-labs-backpack']");
        page.click("[data-test='shopping-cart-link']");
        assertThat(page.locator("[data-test='title']")).hasText("Your Cart");
    }
}