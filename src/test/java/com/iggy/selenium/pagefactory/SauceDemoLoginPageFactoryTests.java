package com.iggy.selenium.pagefactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoLoginPageFactoryTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private SauceDemoLoginPageFactory loginPage;
    private static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
        loginPage = new SauceDemoLoginPageFactory(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldLoginWithValidCredentials() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    void shouldShowErrorWithInvalidCredentials() {
        loginPage.enterUsername("wrong_user");
        loginPage.enterPassword("wrong_pass");
        loginPage.clickLoginButton();
        assertTrue(loginPage.getErrorMessage().contains("Epic sadface"));
    }
}
