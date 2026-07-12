package com.iggy.selenium.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FileUploadPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public FileUploadPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By fileInput = By.id("file-upload");
    private By uploadButton = By.id("file-submit");
    private By uploadedFileName = By.id("uploaded-files");

    public void uploadFile(String filePath) {
        driver.findElement(fileInput).sendKeys(filePath);
    }

    public void clickUpload() {
        driver.findElement(uploadButton).click();
    }

    public String getUploadedFileName() {
        return wait.until(ExpectedConditions
                .presenceOfElementLocated(uploadedFileName)).getText();
    }
}