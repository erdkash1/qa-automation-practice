package com.iggy.selenium.saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoLoginParameterizedTests {

    private WebDriver driver;
    private SauceDemoLoginPage loginPage;
    private static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.get(BASE_URL);
        loginPage = new SauceDemoLoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @ParameterizedTest
    @CsvSource({
            "standard_user, secret_sauce, inventory",
            "locked_out_user, secret_sauce, Epic sadface",
            "wrong_user, wrong_pass, Epic sadface"
    })
    void shouldHandleLoginScenarios(String username, String password, String expected) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        if(driver.getCurrentUrl().contains("inventory")){
            assertTrue(driver.getCurrentUrl().contains(expected));
        }else{
            assertTrue(loginPage.getErrorMessage().contains(expected));
        }
    }
}