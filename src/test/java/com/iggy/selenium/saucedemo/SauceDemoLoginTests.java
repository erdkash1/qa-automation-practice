package com.iggy.selenium.saucedemo;

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

public class SauceDemoLoginTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private SauceDemoLoginPage sauceDemoLoginPage;
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
        sauceDemoLoginPage = new SauceDemoLoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldLoginWithValidCredentials(){
        sauceDemoLoginPage.enterUsername("standard_user");
        sauceDemoLoginPage.enterPassword("secret_sauce");
        sauceDemoLoginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }


    @Test
    void shouldShowErrorWithInvalidCredentials(){
        sauceDemoLoginPage.enterUsername("wrong_user");
        sauceDemoLoginPage.enterPassword("wrong_pass");
        sauceDemoLoginPage.clickLoginButton();
        String errorText = sauceDemoLoginPage.getErrorMessage();
        assertTrue(errorText.contains("Epic sadface"));
    }

}
