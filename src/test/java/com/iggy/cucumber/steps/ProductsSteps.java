package com.iggy.cucumber.steps;

import com.iggy.selenium.saucedemo.SauceDemoLoginPage;
import com.iggy.selenium.saucedemo.SauceDemoProductsPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsSteps {

    private final SharedContext context;

    public ProductsSteps(SharedContext context) {
        this.context = context;
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        context.driver = new ChromeDriver(options);
    }

    @After
    public void tearDown() {
        if (context.driver != null) context.driver.quit();
    }

    @Given("I am logged in as {string} with password {string}")
    public void iAmLoggedInAs(String username, String password) {
        context.driver.get("https://www.saucedemo.com");
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(context.driver);
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        context.productsPage = new SauceDemoProductsPage(context.driver);
    }

    @Then("I should see the page title {string}")
    public void iShouldSeeThePageTitle(String expectedTitle) {
        assertEquals(expectedTitle, context.productsPage.getPageTitle());
    }

    @Then("I should see {int} products")
    public void iShouldSeeProducts(int expectedCount) {
        assertEquals(expectedCount, context.productsPage.getProductCount());
    }

    @Then("the first product should be {string}")
    public void theFirstProductShouldBe(String expectedName) {
        assertEquals(expectedName, context.productsPage.getFirstProductName());
    }

    @When("I sort products by {string}")
    public void iSortProductsBy(String sortOption) {
        context.productsPage.sortByPriceLowToHigh();
    }

    @Then("the first product price should be {string}")
    public void theFirstProductPriceShouldBe(String expectedPrice) {
        assertEquals(expectedPrice, context.productsPage.getFirstProductPrice());
    }
}