package com.iggy.selenium.testng;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import com.iggy.selenium.saucedemo.SauceDemoLoginPage;

public class SauceDemoLoginTestNG {

    private WebDriver driver;
    private SauceDemoLoginPage loginPage;
    private static final String BASE_URL = "https://www.saucedemo.com";

    @BeforeMethod
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

    @AfterMethod
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
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Test
    void shouldShowErrorWithInvalidCredentials() {
        loginPage.enterUsername("wrong_user");
        loginPage.enterPassword("wrong_pass");
        loginPage.clickLoginButton();
        String errorText = loginPage.getErrorMessage();
        Assert.assertTrue(errorText.contains("Epic sadface"));
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                {"standard_user", "secret_sauce", "inventory"},
                {"locked_out_user", "secret_sauce", "Epic sadface"},
                {"wrong_user", "wrong_pass", "Epic sadface"}
        };
    }

    @Test(dataProvider = "loginData")
    void shouldHandleMultipleLoginScenarios(
            String username, String password, String expected) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        if (driver.getCurrentUrl().contains("inventory")) {
            Assert.assertTrue(driver.getCurrentUrl().contains(expected));
        } else {
            Assert.assertTrue(loginPage.getErrorMessage().contains(expected));
        }
    }
}