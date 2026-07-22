package com.iggy.selenium.saucedemo;

import com.iggy.utils.ScreenshotOnFailureExtension;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("SauceDemo E-Commerce")
@Feature("Login")
public class SauceDemoLoginTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private SauceDemoLoginPage sauceDemoLoginPage;


    @RegisterExtension
    ScreenshotOnFailureExtension screenshotExtension =
            new ScreenshotOnFailureExtension(() -> driver);
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
        sauceDemoLoginPage = new SauceDemoLoginPage(driver);
    }

    private boolean testFailed = false;

    @AfterEach
    void tearDown(TestInfo testInfo) {
        if (testFailed && driver != null) {
            try {
                String testName = testInfo.getDisplayName()
                        .replaceAll("[^a-zA-Z0-9_-]", "_");
                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
                File screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);
                Files.createDirectories(Paths.get("screenshots"));
                Files.copy(screenshot.toPath(),
                        Paths.get("screenshots/" + testName + "_" + timestamp + ".png"));
                System.out.println("✅ Screenshot saved!");
            } catch (IOException e) {
                System.err.println("Screenshot failed: " + e.getMessage());
            }
        }
        if (driver != null) driver.quit();
    }

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    void shouldLoginWithValidCredentials(){
        sauceDemoLoginPage.enterUsername("standard_user");
        sauceDemoLoginPage.enterPassword("secret_sauce");
        sauceDemoLoginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }


    @Test
    @Story("Invalid Login")
    @Severity(SeverityLevel.CRITICAL)
    void shouldShowErrorWithInvalidCredentials(){
        sauceDemoLoginPage.enterUsername("wrong_user");
        sauceDemoLoginPage.enterPassword("wrong_pass");
        sauceDemoLoginPage.clickLoginButton();
        String errorText = sauceDemoLoginPage.getErrorMessage();
        assertTrue(errorText.contains("Epic sadface"));
    }

    @Test
    void shouldLoginOnFirefox() {
        if (driver != null) driver.quit();

        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        driver.get("https://www.saucedemo.com");
        sauceDemoLoginPage = new SauceDemoLoginPage(driver);

        sauceDemoLoginPage.enterUsername("standard_user");
        sauceDemoLoginPage.enterPassword("secret_sauce");
        sauceDemoLoginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

}
