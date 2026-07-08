package com.iggy.selenium.saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SauceDemoProductsTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private SauceDemoLoginPage sauceDemoLoginPage;
    private SauceDemoProductsPage sauceDemoProductsPage;
    private static final String BASE_URL = "https://www.saucedemo.com";


    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(BASE_URL);
        sauceDemoLoginPage = new SauceDemoLoginPage(driver);
        sauceDemoLoginPage.enterUsername("standard_user");
        sauceDemoLoginPage.enterPassword("secret_sauce");
        sauceDemoLoginPage.clickLoginButton();
        sauceDemoProductsPage = new SauceDemoProductsPage(driver);
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldDisplayProductsTitle(){
        assertEquals("Products", sauceDemoProductsPage.getPageTitle());

    }

    @Test
    void shouldDisplaySixProducts(){
        assertEquals(6, sauceDemoProductsPage.getProductCount());
    }

    @Test
    void shouldDisplayFirstProduct(){
        assertEquals("Sauce Labs Backpack", sauceDemoProductsPage.getFirstProductName());
    }

    @Test
    void shouldUpdateCartBadgeAfterAddingOneItem() {
        sauceDemoProductsPage.addBackpackToCart();
        assertEquals("1", sauceDemoProductsPage.getCartBadgeCount());
    }

    @Test
    void shouldUpdateCartBadgeAfterAddingTwoItems() {
        sauceDemoProductsPage.addBackpackToCart();
        sauceDemoProductsPage.addBikeLightToCart();
        assertEquals("2", sauceDemoProductsPage.getCartBadgeCount());
    }

    @Test
    void shouldSortProductsByPriceLowToHigh() {
        sauceDemoProductsPage.sortByPriceLowToHigh();
        assertEquals("$7.99", sauceDemoProductsPage.getFirstProductPrice());

    }

    @Test
    void shouldVerifyProductsPageCompletely() {
        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(sauceDemoProductsPage.getPageTitle())
                .isEqualTo("Products");

        soft.assertThat(sauceDemoProductsPage.getProductCount())
                .isEqualTo(6);

        soft.assertThat(sauceDemoProductsPage.getFirstProductName())
                .isEqualTo("Sauce Labs Backpack");

        soft.assertAll();
    }
}
