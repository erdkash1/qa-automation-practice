package com.iggy.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Execution(ExecutionMode.CONCURRENT)
public class ParallelPlaywrightTests {

    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        page = browser.newPage();
    }

    @AfterEach
    void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    void shouldLoginTest1() {
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        assertThat(page).hasURL(java.util.regex.Pattern.compile(".*inventory.*"));
        System.out.println("Test 1 passed on thread: " +
                Thread.currentThread().getName());
    }

    @Test
    void shouldLoginTest2() {
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        assertThat(page.locator("[data-test='title']")).hasText("Products");
        System.out.println("Test 2 passed on thread: " +
                Thread.currentThread().getName());
    }

    @Test
    void shouldLoginTest3() {
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "wrong_user");
        page.fill("[data-test='password']", "wrong_pass");
        page.click("[data-test='login-button']");
        assertThat(page.locator("[data-test='error']"))
                .containsText("Epic sadface");
        System.out.println("Test 3 passed on thread: " +
                Thread.currentThread().getName());
    }

    @Test
    void shouldLoginTest4() {
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        page.click("[data-test='add-to-cart-sauce-labs-backpack']");
        assertThat(page.locator("[data-test='shopping-cart-badge']"))
                .hasText("1");
        System.out.println("Test 4 passed on thread: " +
                Thread.currentThread().getName());
    }
}
