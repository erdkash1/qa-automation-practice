package com.iggy.selenium.theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JavaScriptAlertsPage {


    private WebDriver driver;
    private WebDriverWait wait;

    public JavaScriptAlertsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By jsAlertButton = By.xpath("//button[text()='Click for JS Alert']");
    private By jsConfirmButton = By.xpath("//button[text()='Click for JS Confirm']");
    private By jsPromptButton = By.xpath("//button[text()='Click for JS Prompt']");
    private By resultText = By.id("result");


    public void clickJsAlert(){
        driver.findElement(jsAlertButton).click();
    }

    public void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().dismiss();
    }

    public String getResultText(){
        return wait.until(ExpectedConditions.presenceOfElementLocated(resultText)).getText();
    }

    public void clickJsConfirm(){
        driver.findElement(jsConfirmButton).click();
    }

    public void clickJsPrompt(){
        driver.findElement(jsPromptButton).click();
    }
}



