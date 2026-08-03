package com.iggy.playwright;

import com.microsoft.playwright.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.SAME_THREAD)
public class PerformanceTests {

    private static Playwright playwright;
    private static Browser browser;
    private Page page;

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(true)
        );
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

    @Test
    void shouldLoadLoginPageWithin3Seconds() {
        long startTime = System.currentTimeMillis();
        page.navigate("https://www.saucedemo.com");
        long endTime = System.currentTimeMillis();

        long loadTime = endTime - startTime;
        System.out.println("Login page load time: " + loadTime + "ms");

        assertTrue(loadTime < 3000,
                "Login page took too long: " + loadTime + "ms (max: 3000ms)");
    }

    @Test
    void shouldMeasureNavigationTiming() {
        page.navigate("https://www.saucedemo.com");

        String timingJson = (String) page.evaluate("""
            () => {
                const timing = performance.timing;
                return JSON.stringify({
                    domContentLoaded: timing.domContentLoadedEventEnd - timing.navigationStart,
                    loadComplete: timing.loadEventEnd - timing.navigationStart,
                    domInteractive: timing.domInteractive - timing.navigationStart,
                    responseTime: timing.responseEnd - timing.requestStart
                });
            }
            """);

        JsonObject timing = JsonParser.parseString(timingJson).getAsJsonObject();

        long domContentLoaded = timing.get("domContentLoaded").getAsLong();
        long loadComplete = timing.get("loadComplete").getAsLong();
        long domInteractive = timing.get("domInteractive").getAsLong();
        long responseTime = timing.get("responseTime").getAsLong();

        System.out.println("   Performance Metrics:");
        System.out.println("   DOM Content Loaded: " + domContentLoaded + "ms");
        System.out.println("   Load Complete:      " + loadComplete + "ms");
        System.out.println("   DOM Interactive:    " + domInteractive + "ms");
        System.out.println("   Response Time:      " + responseTime + "ms");

        assertTrue(domContentLoaded < 3000, "DOM content loaded too slow!");
        assertTrue(loadComplete < 5000, "Page load too slow!");
        assertTrue(responseTime < 2000, "Server response too slow!");
    }

    @Test
    void shouldCountNetworkRequests() {
        int[] requestCount = {0};

        page.onRequest(request -> {
            requestCount[0]++;
            System.out.println(" Request: " + request.url());
        });

        page.navigate("https://www.saucedemo.com");

        System.out.println(" Total requests: " + requestCount[0]);
        assertTrue(requestCount[0] > 0, "No network requests made!");
        assertTrue(requestCount[0] < 50, "Too many requests: " + requestCount[0]);
    }

    @Test
    void shouldMeasureProductsPageLoadAfterLogin() {
        // Login first
        page.navigate("https://www.saucedemo.com");
        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");

        // Measure products page load time
        long startTime = System.currentTimeMillis();
        page.click("[data-test='login-button']");
        page.waitForURL("**/inventory.html");
        long endTime = System.currentTimeMillis();

        long loadTime = endTime - startTime;
        System.out.println(" Products page load time: " + loadTime + "ms");

        assertTrue(loadTime < 5000,
                "Products page too slow: " + loadTime + "ms");
    }

    @Test
    void shouldMeasurePageSize() {
        int[] totalBytes = {0};

        page.onResponse(response -> {
            try {
                byte[] body = response.body();
                totalBytes[0] += body.length;
            } catch (Exception e) {
            }
        });

        page.navigate("https://www.saucedemo.com");

        double totalKB = totalBytes[0] / 1024.0;
        System.out.println(" Total page size: " +
                String.format("%.2f", totalKB) + " KB");

        assertTrue(totalKB < 5000,
                "Page too large: " + totalKB + " KB");
    }
}