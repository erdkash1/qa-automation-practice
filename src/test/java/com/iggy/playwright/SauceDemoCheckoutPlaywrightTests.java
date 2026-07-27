package com.iggy.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SauceDemoCheckoutPlaywrightTests {

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
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        page.click("[data-test='add-to-cart-sauce-labs-backpack']");
        page.click("[data-test='shopping-cart-link']");
    }

    @AfterEach
    void closePage() {
        page.close();
    }

    @Test
    void shouldCompleteFullCheckoutFlow() {
        page.click("[data-test='checkout']");

        page.fill("[data-test='firstName']", "Iggy");
        page.fill("[data-test='lastName']", "Shirmen");
        page.fill("[data-test='postalCode']", "65806");

        page.click("[data-test='continue']");
        page.click("[data-test='finish']");
        assertThat(page.locator("[data-test='complete-header']"))
                .hasText("Thank you for your order!");
    }

    @Test
    void shouldVerifyCartContentsBeforeCheckout() {
        assertThat(page.locator("[data-test='inventory-item']"))
                .hasCount(1);

        assertThat(page.locator("[data-test='inventory-item-name']"))
                .hasText("Sauce Labs Backpack");

        assertThat(page.locator("[data-test='title']"))
                .hasText("Your Cart");
    }

    @Test
    void shouldVerifyCheckoutFormValidation() {
        page.click("[data-test='checkout']");
        page.click("[data-test='continue']");
        assertThat(page.locator("[data-test='error']"))
                .containsText("First Name is required");
    }
}