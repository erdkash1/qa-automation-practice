package com.iggy.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SauceDemoLoginPagePlaywrightTests {

    private Page page;
    private SauceDemoLoginPagePlaywright loginPage;
    private static Playwright playwright;
    private static Browser browser;

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
        loginPage = new SauceDemoLoginPagePlaywright(page);
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
        assertThat(page).hasURL(Pattern.compile(".*inventory.*")); // ← Playwright assertion
    }

    @Test
    void shouldShowErrorWithInvalidCredentials() {
        page.fill("[data-test='username']", "wrong_user");
        page.fill("[data-test='password']", "wrong_pass");
        page.click("[data-test='login-button']");
        assertThat(page.locator("[data-test='error']"))
                .containsText("Epic sadface"); // ← Playwright assertion
    }

    @Test
    void shouldDisplayProductsPageTitle() {
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        assertThat(page.locator("[data-test='title']"))
                .hasText("Products"); // ← Playwright assertion
    }
}