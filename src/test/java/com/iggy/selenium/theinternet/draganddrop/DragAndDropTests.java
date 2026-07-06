package com.iggy.selenium.theinternet.draganddrop;

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

public class DragAndDropTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private DragAndDropPage dragAndDropPage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/drag_and_drop";


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
        dragAndDropPage = new DragAndDropPage(driver);

    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldSwapColumnsAfterDrag(){
        dragAndDropPage.dragAtoB();
        assertEquals("B", dragAndDropPage.getColumnAText());
        assertEquals("A", dragAndDropPage.getColumnBText());

    }
}
