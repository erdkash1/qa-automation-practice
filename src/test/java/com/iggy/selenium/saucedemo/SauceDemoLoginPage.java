package com.iggy.selenium.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoLoginPage {


    private WebDriver driver;
    private WebDriverWait wait;


    public SauceDemoLoginPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By usernameField = By.cssSelector("[data-test='username']");
    private By passwordField = By.cssSelector("[data-test='password']");
    private By loginButton = By.cssSelector("[data-test='login-button']");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.presenceOfElementLocated(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password){
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton(){
        driver.findElement(loginButton).click();
    }

    public String getErrorMessage(){
        return wait.until(ExpectedConditions.presenceOfElementLocated(errorMessage)).getText();
    }


}
