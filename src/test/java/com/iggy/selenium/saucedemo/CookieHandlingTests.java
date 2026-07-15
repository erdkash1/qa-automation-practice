package com.iggy.selenium.saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertTrue;

public class CookieHandlingTests {

    private WebDriver driver;
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
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    void shouldReadExistingCookies() {
        driver.findElement(
                        org.openqa.selenium.By.cssSelector("[data-test='username']"))
                .sendKeys("standard_user");
        driver.findElement(
                        org.openqa.selenium.By.cssSelector("[data-test='password']"))
                .sendKeys("secret_sauce");
        driver.findElement(
                        org.openqa.selenium.By.cssSelector("[data-test='login-button']"))
                .click();

        var cookies = driver.manage().getCookies();
        System.out.println("🍪 Cookies after login:");
        cookies.forEach(c -> System.out.println("  " + c.getName() + " = " + c.getValue()));

        Cookie sessionCookie = driver.manage().getCookieNamed("session-username");
        assertTrue(sessionCookie != null, "Session cookie should exist after login");
    }

    @Test
    void shouldDeleteCookieAndLogout() {
        driver.findElement(
                        org.openqa.selenium.By.cssSelector("[data-test='username']"))
                .sendKeys("standard_user");
        driver.findElement(
                        org.openqa.selenium.By.cssSelector("[data-test='password']"))
                .sendKeys("secret_sauce");
        driver.findElement(
                        org.openqa.selenium.By.cssSelector("[data-test='login-button']"))
                .click();

        assertTrue(driver.getCurrentUrl().contains("inventory"));

        driver.manage().deleteCookieNamed("session-username");

        driver.navigate().refresh();
        assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
        System.out.println("✅ Cookie deleted — user logged out!");
    }
}
