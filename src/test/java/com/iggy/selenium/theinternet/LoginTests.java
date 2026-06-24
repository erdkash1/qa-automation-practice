package com.iggy.selenium.theinternet;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LoginTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/login";

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
        loginPage = new LoginPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldLoginWithValidCredentials() {
        driver.get(BASE_URL);
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash")));        String messageText = message.getText();
        assertTrue(messageText.contains("You logged into a secure area!"));
    }

    @Test
    void shouldShowErrorWithInvalidUsername() {
        driver.get(BASE_URL);
        driver.findElement(By.id("username")).sendKeys("wronguser");
        driver.findElement(By.id("password")).sendKeys("password123!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash")));        String messageText = message.getText();
        assertTrue(messageText.contains("Your username is invalid!"));


    }
}