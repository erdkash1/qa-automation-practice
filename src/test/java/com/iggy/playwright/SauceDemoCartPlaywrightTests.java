package com.iggy.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SauceDemoCartPlaywrightTests {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;

    @BeforeAll
    static void launchBrowser(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
    }

    @AfterAll
    static void closeBrowser(){
        playwright.close();
    }

    @BeforeEach
    void createPage() {
        page = browser.newPage();
        page.context().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true));
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
    }

    @AfterEach
    void closePage() {
        page.context().tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("traces/trace.zip")));
        page.close();
    }

    @Test
    void shouldAddItemToCart(){
        page.click("[data-test='add-to-cart-sauce-labs-backpack']");
        assertThat(page.locator("[data-test='shopping-cart-badge']")).hasText("1");
    }

    @Test
    void shouldNavigateToCart(){
        page.click("[data-test='add-to-cart-sauce-labs-backpack']");
        page.click("[data-test='shopping-cart-link']");
        assertThat(page.locator("[data-test='title']"))
                .hasText("Your Cart");
    }


}
