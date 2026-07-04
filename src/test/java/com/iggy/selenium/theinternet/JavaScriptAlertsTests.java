package com.iggy.selenium.theinternet;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaScriptAlertsTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private JavaScriptAlertsPage javaScriptAlertsPage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/javascript_alerts";

    @BeforeEach
    void setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get(BASE_URL);
        javaScriptAlertsPage = new JavaScriptAlertsPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldAcceptJsAlert(){
        javaScriptAlertsPage.clickJsAlert();
        javaScriptAlertsPage.acceptAlert();
        assertEquals("You successfully clicked an alert", javaScriptAlertsPage.getResultText());
    }

    @Test
    void shouldAcceptJsConfirm(){
        javaScriptAlertsPage.clickJsConfirm();
        javaScriptAlertsPage.acceptAlert();
        assertEquals("You clicked: Ok", javaScriptAlertsPage.getResultText());
    }

    @Test
    void shouldDismissJsConfirm(){
        javaScriptAlertsPage.clickJsConfirm();
        javaScriptAlertsPage.dismissAlert();
        assertEquals("You clicked: Cancel", javaScriptAlertsPage.getResultText());
    }
}
