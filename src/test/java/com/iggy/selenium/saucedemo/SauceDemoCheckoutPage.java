package com.iggy.selenium.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SauceDemoCheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public SauceDemoCheckoutPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By checkoutButton = By.cssSelector("[data-test='checkout']");
    private By firstNameField = By.cssSelector("[data-test='firstName']");
    private By lastNameField = By.cssSelector("[data-test='lastName']");
    private By postalCodeField = By.cssSelector("[data-test='postalCode']");
    private By continueButton = By.cssSelector("[data-test='continue']");
    private By finishButton = By.cssSelector("[data-test='finish']");
    private By confirmationHeader = By.cssSelector("[data-test='complete-header']");


    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(firstNameField));
    }

    public void enterFirstName(String name){
        wait.until(ExpectedConditions.presenceOfElementLocated(firstNameField)).sendKeys(name);
    }

    public void enterLastName(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(lastNameField)).sendKeys(name);
    }

    public void enterPostalCode(String code) {
        wait.until(ExpectedConditions.presenceOfElementLocated(postalCodeField)).sendKeys(code);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.presenceOfElementLocated(finishButton)).click();
    }

    public String getConfirmationMessage(){
        return wait.until(ExpectedConditions.presenceOfElementLocated(confirmationHeader)).getText();
    }

}
