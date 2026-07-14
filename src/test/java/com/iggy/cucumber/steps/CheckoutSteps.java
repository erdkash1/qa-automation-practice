package com.iggy.cucumber.steps;

import com.iggy.selenium.saucedemo.SauceDemoCheckoutPage;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutSteps {

    private final SharedContext context;
    private SauceDemoCheckoutPage checkoutPage;

    public CheckoutSteps(SharedContext context) {
        this.context = context;
    }

    @When("I click checkout")
    public void iClickCheckout() {
        checkoutPage = new SauceDemoCheckoutPage(context.driver);
        checkoutPage.clickCheckout();
    }

    @When("I enter first name {string}")
    public void iEnterFirstName(String firstName) {
        checkoutPage.enterFirstName(firstName);
    }

    @When("I enter last name {string}")
    public void iEnterLastName(String lastName) {
        checkoutPage.enterLastName(lastName);
    }

    @When("I enter postal code {string}")
    public void iEnterPostalCode(String postalCode) {
        checkoutPage.enterPostalCode(postalCode);
    }

    @When("I click continue")
    public void iClickContinue() {
        checkoutPage.clickContinue();
    }

    @When("I click finish")
    public void iClickFinish() {
        checkoutPage.clickFinish();
    }

    @Then("I should see the confirmation message {string}")
    public void iShouldSeeTheConfirmationMessage(String expectedMessage) {
        assertEquals(expectedMessage, checkoutPage.getConfirmationMessage());
    }
}