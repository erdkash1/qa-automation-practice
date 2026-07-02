package com.iggy.selenium.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SauceDemoProductsPage {

    private WebDriver driver;
    private WebDriverWait wait;


    public SauceDemoProductsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    private By pageTitle = By.cssSelector("[data-test='title']");
    private By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private By inventoryItems = By.cssSelector("[data-test='inventory-item']");
    private By itemNames = By.cssSelector("[data-test='inventory-item-name']");
    private By itemPrices = By.cssSelector("[data-test='inventory-item-price']");

    public String getPageTitle(){
        return wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
    }

    public Integer getProductCount(){
        return driver.findElements(inventoryItems).size();

    }

    public String getFirstProductName(){
        return driver.findElements(itemNames).get(0).getText();
    }

}
