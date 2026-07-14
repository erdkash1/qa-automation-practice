package com.iggy.cucumber.steps;

import com.iggy.selenium.saucedemo.SauceDemoCartPage;
import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;

public class CartSteps {

    private final SharedContext context;

    public CartSteps(SharedContext context) {
        this.context = context;
    }

    @When("I add {string} to cart")
    public void iAddToCart(String productName) {
        if (productName.equals("Sauce Labs Backpack")) {
            context.productsPage.addBackpackToCart();
        } else if (productName.equals("Sauce Labs Bike Light")) {
            context.productsPage.addBikeLightToCart();
        }
    }

    @Then("the cart badge should show {string}")
    public void theCartBadgeShouldShow(String expectedCount) {
        assertEquals(expectedCount, context.productsPage.getCartBadgeCount());
    }

    @When("I go to the cart page")
    public void iGoToTheCartPage() {
        context.cartPage = new SauceDemoCartPage(context.driver);
        context.cartPage.clickCartIcon();
    }

    @When("I remove the first item")
    public void iRemoveTheFirstItem() {
        context.cartPage.removeFirstItem();
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        assertEquals(0, context.cartPage.getCartItemCount());
    }
}