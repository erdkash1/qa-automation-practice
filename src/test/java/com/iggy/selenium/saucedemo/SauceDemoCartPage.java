package com.iggy.selenium.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoCartPage{

    private WebDriver driver;
    private WebDriverWait wait;


    public SauceDemoCartPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By cartTitle = By.cssSelector("[data-test='title']");
    private By cartItems = By.cssSelector("[data-test='inventory-item']");
    private By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private By itemPrices = By.cssSelector("[data-test='inventory-item-price']");
    private By removeButton = By.cssSelector("[class='btn btn_secondary btn_small cart_button']");
    private By cartIcon = By.cssSelector("[data-test='shopping-cart-link']");



    public String getCartTitle(){
        return wait.until(ExpectedConditions.presenceOfElementLocated(cartTitle)).getText();
    }

    public Integer getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public String getFirstItemName(){
        return driver.findElements(itemNames).get(0).getText();
    }

    public void clickCartIcon(){
        driver.findElement(cartIcon).click();
    }
    public void removeFirstItem(){
        driver.findElement(removeButton).click();
    }
}
