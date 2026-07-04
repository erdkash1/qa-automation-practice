package com.iggy.selenium.theinternet;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicLoadingTests {

    private WebDriver driver;
    private DynamicLoadingPage dynamicLoadingPage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/dynamic_loading/1";

    @BeforeEach
    void setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get(BASE_URL);
        dynamicLoadingPage = new DynamicLoadingPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldDisplayHelloWorldAfterLoading(){
        dynamicLoadingPage.clickStart();
        assertEquals("Hello World!", dynamicLoadingPage.getFinishText());
    }
}
