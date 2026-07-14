package com.iggy.cucumber.steps;

import com.iggy.selenium.saucedemo.SauceDemoCartPage;
import com.iggy.selenium.saucedemo.SauceDemoCheckoutPage;
import com.iggy.selenium.saucedemo.SauceDemoProductsPage;
import org.openqa.selenium.WebDriver;

public class SharedContext {
    public WebDriver driver;
    public SauceDemoProductsPage productsPage;
    public SauceDemoCartPage cartPage;
    public SauceDemoCheckoutPage checkoutPage;
}