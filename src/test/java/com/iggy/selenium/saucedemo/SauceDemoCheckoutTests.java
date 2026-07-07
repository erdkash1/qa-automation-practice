package com.iggy.selenium.saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Tag("flaky")
public class SauceDemoCheckoutTests {

    private WebDriver driver;
    private WebDriverWait wait;
    private SauceDemoCartPage sauceDemoCartPage;
    private SauceDemoProductsPage sauceDemoProductsPage;
    private SauceDemoCheckoutPage sauceDemoCheckoutPage;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.get(BASE_URL);
        SauceDemoLoginPage loginPage = new SauceDemoLoginPage(driver);
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();
        sauceDemoProductsPage = new SauceDemoProductsPage(driver);
        sauceDemoProductsPage.addBackpackToCart();
        sauceDemoCartPage = new SauceDemoCartPage(driver);
        sauceDemoCartPage.clickCartIcon();
        sauceDemoCheckoutPage = new SauceDemoCheckoutPage(driver);
        sauceDemoCheckoutPage.clickCheckout();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void shouldCompleteFullCheckoutFlow(){
        sauceDemoCheckoutPage.enterFirstName("Iggy");
        sauceDemoCheckoutPage.enterLastName("Shirmen");
        sauceDemoCheckoutPage.enterPostalCode("65806");
        sauceDemoCheckoutPage.clickContinue();
        sauceDemoCheckoutPage.clickFinish();
        assertEquals("Thank you for your order!", sauceDemoCheckoutPage.getConfirmationMessage());

    }
}
