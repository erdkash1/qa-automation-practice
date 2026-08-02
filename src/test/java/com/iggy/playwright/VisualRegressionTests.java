package com.iggy.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.SAME_THREAD)
public class VisualRegressionTests {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;
    private static final String BASELINE_DIR = "visual-baselines/";
    private static final String ACTUAL_DIR = "visual-actual/";

    @BeforeAll
    static void setUp() throws IOException {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
        Files.createDirectories(Paths.get(BASELINE_DIR));
        Files.createDirectories(Paths.get(ACTUAL_DIR));
    }

    @AfterAll
    static void tearDown() {
        playwright.close();
    }

    @BeforeEach
    void createPage() {
        page = browser.newPage();
        page.setViewportSize(1280, 720);
    }

    @AfterEach
    void closePage() {
        page.close();
    }

    private byte[] takeScreenshot(String name) {
        return page.screenshot(new Page.ScreenshotOptions()
                .setFullPage(false));
    }

    private void saveBaseline(String name, byte[] screenshot) throws IOException {
        Files.write(Paths.get(BASELINE_DIR + name + ".png"), screenshot);
        System.out.println("Baseline saved: " + name);
    }

    private boolean compareScreenshots(byte[] baseline, byte[] actual)
            throws IOException {
        BufferedImage baselineImg = ImageIO.read(
                new ByteArrayInputStream(baseline));
        BufferedImage actualImg = ImageIO.read(
                new ByteArrayInputStream(actual));

        if (baselineImg.getWidth() != actualImg.getWidth() ||
                baselineImg.getHeight() != actualImg.getHeight()) {
            return false;
        }

        int totalPixels = baselineImg.getWidth() * baselineImg.getHeight();
        int differentPixels = 0;

        for (int x = 0; x < baselineImg.getWidth(); x++) {
            for (int y = 0; y < baselineImg.getHeight(); y++) {
                if (baselineImg.getRGB(x, y) != actualImg.getRGB(x, y)) {
                    differentPixels++;
                }
            }
        }

        double differencePercent = (double) differentPixels / totalPixels * 100;
        System.out.println("Pixel difference: " +
                String.format("%.2f", differencePercent) + "%");

        return differencePercent < 1.0;
    }

    @Test
    void shouldMatchLoginPageBaseline() throws IOException {
        page.navigate("https://www.saucedemo.com");

        String testName = "login-page";
        Path baselinePath = Paths.get(BASELINE_DIR + testName + ".png");

        byte[] screenshot = takeScreenshot(testName);

        if (!Files.exists(baselinePath)) {
            saveBaseline(testName, screenshot);
            System.out.println("Baseline created — run again to compare!");
        } else {
            byte[] baseline = Files.readAllBytes(baselinePath);
            Files.write(Paths.get(ACTUAL_DIR + testName + ".png"), screenshot);

            boolean matches = compareScreenshots(baseline, screenshot);
            assertTrue(matches,
                    "Login page visual regression detected! Check visual-actual/ folder");
            System.out.println("Login page matches baseline!");
        }
    }

    @Test
    void shouldMatchProductsPageBaseline() throws IOException {
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");

        String testName = "products-page";
        Path baselinePath = Paths.get(BASELINE_DIR + testName + ".png");

        byte[] screenshot = takeScreenshot(testName);

        if (!Files.exists(baselinePath)) {
            saveBaseline(testName, screenshot);
            System.out.println("Baseline created — run again to compare!");
        } else {
            byte[] baseline = Files.readAllBytes(baselinePath);
            Files.write(Paths.get(ACTUAL_DIR + testName + ".png"), screenshot);

            boolean matches = compareScreenshots(baseline, screenshot);
            assertTrue(matches,
                    "Products page visual regression detected!");
            System.out.println("Products page matches baseline!");
        }
    }

    @Test
    void shouldMatchCartPageBaseline() throws IOException {
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");
        page.click("[data-test='add-to-cart-sauce-labs-backpack']");
        page.click("[data-test='shopping-cart-link']");

        String testName = "cart-page";
        Path baselinePath = Paths.get(BASELINE_DIR + testName + ".png");

        byte[] screenshot = takeScreenshot(testName);

        if (!Files.exists(baselinePath)) {
            saveBaseline(testName, screenshot);
            System.out.println("Baseline created — run again to compare!");
        } else {
            byte[] baseline = Files.readAllBytes(baselinePath);
            Files.write(Paths.get(ACTUAL_DIR + testName + ".png"), screenshot);

            boolean matches = compareScreenshots(baseline, screenshot);
            assertTrue(matches,
                    "Cart page visual regression detected!");
            System.out.println("Cart page matches baseline!");
        }
    }
}