package com.iggy.selenium.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DynamicLoadingPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public DynamicLoadingPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By startButton = By.cssSelector("#start button");
    private By loadingIndicator = By.id("loading");
    private By finishText = By.id("finish");

    public void clickStart(){
        driver.findElement(startButton).click();
    }

    public String getFinishText(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(finishText)).getText();
    }
}
