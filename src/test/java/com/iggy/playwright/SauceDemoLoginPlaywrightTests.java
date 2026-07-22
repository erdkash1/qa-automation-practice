package com.iggy.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.*;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void shouldShowErrorWithInvalidCredentials(){
        page.fill("[data-test='username']", "wrong_user");
        page.fill("[data-test='password']","wrong_pass");
        page.click("[data-test='login-button']");
        assertTrue(page.locator("[data-test='error']").textContent().contains("Epic sadface"));
    }

    @Test
    void shouldDisplayProductsPageTitle(){
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        assertTrue(page.locator("[data-test='title']").textContent().contains("Products"));
    }

    @Test
    void shouldLoginOnFirefox() {
        try (Playwright pw = Playwright.create()) {
            Browser firefoxBrowser = pw.firefox().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
            Page firefoxPage = firefoxBrowser.newPage();
            firefoxPage.navigate("https://www.saucedemo.com");
            firefoxPage.fill("[data-test='username']", "standard_user");
            firefoxPage.fill("[data-test='password']", "secret_sauce");
            firefoxPage.click("[data-test='login-button']");
            assertThat(firefoxPage).hasURL(Pattern.compile(".*inventory.*"));
            firefoxBrowser.close();
        }
    }

    @Test
    void shouldLoginOnSafari() {
        try (Playwright pw = Playwright.create()) {
            Browser webkitBrowser = pw.webkit().launch(
                    new BrowserType.LaunchOptions().setHeadless(true)
            );
            Page safariPage = webkitBrowser.newPage();
            safariPage.navigate("https://www.saucedemo.com");
            safariPage.fill("[data-test='username']", "standard_user");
            safariPage.fill("[data-test='password']", "secret_sauce");
            safariPage.click("[data-test='login-button']");
            assertThat(safariPage).hasURL(Pattern.compile(".*inventory.*"));
            webkitBrowser.close();
        }
    }
}