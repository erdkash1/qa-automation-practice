package com.iggy.selenium.theinternet.fileupload;

import com.iggy.selenium.theinternet.FileUploadPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUploadTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private FileUploadPage fileUploadPage;
    private static final String BASE_URL = "https://the-internet.herokuapp.com/upload";

    @BeforeEach
    void setup(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get(BASE_URL);
        fileUploadPage = new FileUploadPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldUploadFileSuccessfully(){
        String filePath = System.getProperty("user.dir") +
                "/src/test/resources/test-upload.txt";
       fileUploadPage.uploadFile(filePath);
        fileUploadPage.clickUpload();
        assertEquals("test-upload.txt", fileUploadPage.getUploadedFileName());
    }
}



