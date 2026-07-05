package com.iggy.selenium.theinternet.hover;

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

public class HoverTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private HoverPage hoverPage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/hovers";

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
        hoverPage = new HoverPage(driver);

    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldShowHiddenTextOnHoverForUser1(){
        hoverPage.hoverOverUser(0);
        assertEquals("name: user1", hoverPage.getHiddenText(0));
    }

    @Test
    void shouldShowHiddenTextOnHoverForUser2(){
        hoverPage.hoverOverUser(1);
        assertEquals("name: user2", hoverPage.getHiddenText(1));
    }
}
