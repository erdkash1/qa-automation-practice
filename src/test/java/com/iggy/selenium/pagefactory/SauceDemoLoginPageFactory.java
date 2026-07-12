package com.iggy.selenium.pagefactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SauceDemoLoginPageFactory {

    private WebDriver driver;

    @FindBy(css = "[data-test='username']")
    private WebElement usernameField;

    @FindBy(css = "[data-test='password']")
    private WebElement passwordField;

    @FindBy(css = "[data-test='login-button']")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public SauceDemoLoginPageFactory(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this); // ← initializes all @FindBy fields
    }

    public void enterUsername(String username) {
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }
}