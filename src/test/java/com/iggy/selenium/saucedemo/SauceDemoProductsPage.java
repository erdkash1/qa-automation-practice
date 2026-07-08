package com.iggy.selenium.saucedemo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.*;

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

    private By addToCartBackpack = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private By addToCartBikeLight = By.cssSelector("[data-test='add-to-cart-sauce-labs-bike-light']");
    private By cartBadge = By.cssSelector("[data-test='shopping-cart-badge']");

    public String getPageTitle(){
        return wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).getText();
    }

    public Integer getProductCount(){
        return driver.findElements(inventoryItems).size();

    }

    public String getFirstProductName(){
        return driver.findElements(itemNames).get(0).getText();
    }

    public void sortByPriceLowToHigh() {
        Select dropdown = new Select(driver.findElement(sortDropdown));
        dropdown.selectByValue("lohi");
    }

    public String getFirstProductPrice() {
        return driver.findElements(itemPrices).get(0).getText();
    }

    public void addBackpackToCart(){
        driver.findElement(addToCartBackpack).click();
    }

    public void addBikeLightToCart(){
        driver.findElement(addToCartBikeLight).click();
    }

    public String getCartBadgeCount(){
        return wait.until(ExpectedConditions.presenceOfElementLocated(cartBadge)).getText();
    }

    public String getPageTitleWithFluentWait() {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);

        return fluentWait.until(d -> d.findElement(pageTitle).getText());
    }



}
