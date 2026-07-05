package com.iggy.selenium.theinternet.multiple;

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

public class MultipleWindowsTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private MultipleWindowsPage multipleWindowsPage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/windows";

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
        multipleWindowsPage = new MultipleWindowsPage(driver);

    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    void shouldOpenNewWindow(){
        String originalHandle = driver.getWindowHandle();
        multipleWindowsPage.clickLink();
        multipleWindowsPage.switchToNewWindow(originalHandle);
        assertEquals("New Window", multipleWindowsPage.getPageTitle());
    }

    @Test
    void shouldSwitchBackToOriginalWindow(){
        String originalHandle = driver.getWindowHandle();
        multipleWindowsPage.clickLink();
        multipleWindowsPage.switchToNewWindow(originalHandle);
        multipleWindowsPage.switchToOriginalWindow(originalHandle);
        assertEquals("The Internet", multipleWindowsPage.getPageTitle());
    }
}
