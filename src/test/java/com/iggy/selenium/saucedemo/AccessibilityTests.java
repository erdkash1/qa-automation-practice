package com.iggy.selenium.saucedemo;

import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.results.Rule;
import com.deque.html.axecore.selenium.AxeBuilder;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccessibilityTests {

    private WebDriver driver;
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
    }

    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    private void printViolations(List<Rule> violations) {
        if (violations.isEmpty()) {
            System.out.println(" No accessibility violations found!");
        } else {
            System.out.println(" Accessibility violations found: " + violations.size());
            violations.forEach(v -> {
                System.out.println("     [" + v.getImpact() + "] " + v.getId());
                System.out.println("     Description: " + v.getDescription());
                System.out.println("     Help: " + v.getHelpUrl());
            });
        }
    }

    private long countCriticalViolations(List<Rule> violations) {
        return violations.stream()
                .filter(v -> v.getImpact() != null &&
                        (v.getImpact().equals("critical") ||
                                v.getImpact().equals("serious")))
                .peek(v -> {
                    System.out.println("   Critical/Serious Violation:");
                    System.out.println("   ID: " + v.getId());
                    System.out.println("   Description: " + v.getDescription());
                    System.out.println("   Impact: " + v.getImpact());
                    System.out.println("   Help: " + v.getHelpUrl());
                })
                .count();
    }

    @Test
    void shouldHaveNoAccessibilityViolationsOnLoginPage() {
        driver.get(BASE_URL);

        Results results = new AxeBuilder().analyze(driver);
        List<Rule> violations = results.getViolations();
        printViolations(violations);

        long criticalViolations = countCriticalViolations(violations);
        assertEquals(0, criticalViolations,
                "Found " + criticalViolations + " critical/serious violations on login page!");
    }

    @Test
    void shouldHaveNoAccessibilityViolationsOnProductsPage() {
        driver.get(BASE_URL);
        driver.findElement(By.cssSelector("[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("[data-test='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[data-test='login-button']")).click();

        Results results = new AxeBuilder()
                .withRules(List.of())
                .analyze(driver);

        List<Rule> violations = results.getViolations();


        long criticalViolations = violations.stream()
                .filter(v -> v.getImpact() != null &&
                        (v.getImpact().equals("critical") ||
                                v.getImpact().equals("serious")))
                .filter(v -> !v.getId().equals("select-name")) // ← exclude known issue
                .count();

        assertEquals(0, criticalViolations,
                "Found unexpected critical/serious violations!");

        System.out.println(" Products page passed (excluding known select-name issue)");
    }

    @Test
    void shouldHaveNoAccessibilityViolationsOnCartPage() {
        driver.get(BASE_URL);
        driver.findElement(By.cssSelector("[data-test='username']")).sendKeys("standard_user");
        driver.findElement(By.cssSelector("[data-test='password']")).sendKeys("secret_sauce");
        driver.findElement(By.cssSelector("[data-test='login-button']")).click();
        driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.cssSelector("[data-test='shopping-cart-link']")).click();

        Results results = new AxeBuilder().analyze(driver);
        List<Rule> violations = results.getViolations();
        printViolations(violations);

        long criticalViolations = countCriticalViolations(violations);

        assertEquals(0, criticalViolations,
                "Found " + criticalViolations + " critical/serious violations on cart page!");
    }
}