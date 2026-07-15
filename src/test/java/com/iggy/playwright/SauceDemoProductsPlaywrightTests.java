package com.iggy.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SauceDemoProductsPlaywrightTests {

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
    void createPage(){
        page = browser.newPage();
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
    }

    @AfterEach
    void closePage(){
        page.close();
    }

    @Test
    void shouldDisplayProductsTitle(){
        assertThat(page.locator("[data-test='title']"))
                .hasText("Products");
   }

    @Test
    void shouldDisplay6Products() {
        assertThat(page.locator("[data-test='inventory-item']")).hasCount(6);
    }

    @Test
    void shouldSortByPriceLowToHigh(){
        page.selectOption("[data-test='product-sort-container']", "lohi");
        assertThat(page.locator("[data-test='inventory-item-price']").first())
                .hasText("$7.99");
    }
}
