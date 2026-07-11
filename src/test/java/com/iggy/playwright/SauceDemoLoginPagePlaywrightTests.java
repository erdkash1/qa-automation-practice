package com.iggy.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoLoginPagePlaywrightTests {

    private Page page;
    private SauceDemoLoginPagePlaywright sauceDemoLoginPagePlaywright;
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
        sauceDemoLoginPagePlaywright = new SauceDemoLoginPagePlaywright(page);
        page.navigate("https://www.saucedemo.com");
    }

    @AfterEach
    void closePage() {
        page.close();
    }


    @Test
    void shouldLoginWithValidCredentials(){
        sauceDemoLoginPagePlaywright.enterUsername("standard_user");
        sauceDemoLoginPagePlaywright.enterPassword("secret_sauce");
        sauceDemoLoginPagePlaywright.clickLoginButton();
        assertTrue(sauceDemoLoginPagePlaywright.getCurrentUrl().contains("inventory"));
    }



    @Test
    void shouldShowErrorWithInvalidCredentials(){
        sauceDemoLoginPagePlaywright.enterUsername("wrong_user");
        sauceDemoLoginPagePlaywright.enterPassword("wrong_pass");
        sauceDemoLoginPagePlaywright.clickLoginButton();
        assertTrue(sauceDemoLoginPagePlaywright.getErrorMessage().contains("Epic sadface"));
    }

}
