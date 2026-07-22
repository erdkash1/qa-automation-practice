package com.iggy.selenium.saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoLoginFirefoxTests {

    private WebDriver driver;
    private SauceDemoLoginPage loginPage;
    private static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeEach
    void setUp() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        driver.get(BASE_URL);
        loginPage = new SauceDemoLoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void shouldLoginWithValidCredentialsOnFirefox() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    void shouldShowErrorWithInvalidCredentialsOnFirefox() {
        loginPage.enterUsername("wrong_user");
        loginPage.enterPassword("wrong_pass");
        loginPage.clickLoginButton();
        assertTrue(loginPage.getErrorMessage().contains("Epic sadface"));
    }
}