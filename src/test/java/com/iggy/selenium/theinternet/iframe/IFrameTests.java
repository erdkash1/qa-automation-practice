package com.iggy.selenium.theinternet.iframe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IFrameTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private IFramePage iFramePage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/iframe";

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
        iFramePage = new IFramePage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldReadDefaultEditorText(){
        iFramePage.switchToIFrame();
        assertEquals("Your content goes here.", iFramePage.getEditorText());
        iFramePage.switchToMainContent();
    }

    @Test
    void shouldTypeTextInEditor(){
        iFramePage.switchToIFrame();
        iFramePage.clearEditorText();
        iFramePage.typeInEditor("Hello Selenium!");
        assertEquals("Hello Selenium!", iFramePage.getEditorText());
        iFramePage.switchToMainContent();
    }


}
