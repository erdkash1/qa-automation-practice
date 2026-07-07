package com.iggy.selenium.theinternet.iframe;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class IFramePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public IFramePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By iframe = By.id("mce_0_ifr");
    private By editorBody = By.id("tinymce");

    public void switchToIFrame() {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframe));
    }
    public void switchToMainContent(){
        driver.switchTo().defaultContent();
    }
    public String getEditorText() {
        wait.until(ExpectedConditions.presenceOfElementLocated(editorBody));
        return driver.findElement(editorBody).getText();
    }
    public void clearEditorText() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].innerHTML = '';", driver.findElement(editorBody));
    }
    public void typeInEditor(String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].innerHTML = arguments[1];",
                driver.findElement(editorBody), text);
    }

}
