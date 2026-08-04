package com.iggy.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import static org.testng.AssertJUnit.assertTrue;

public class MobileEmulationTests {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
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
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }


    @BeforeEach
    void createMobilePage() {
        Browser.NewContextOptions mobileOptions = new Browser.NewContextOptions()
                .setViewportSize(390, 844)
                .setDeviceScaleFactor(3.0)
                .setIsMobile(true)
                .setHasTouch(true)
                .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/16.0 Mobile/15E148 Safari/604.1");

        context = browser.newContext(mobileOptions);
        page = context.newPage();
    }
    @AfterEach
    void closeResources() {
        if (page != null) page.close();
        if (context != null) context.close();
    }


    @Test
    void testMobileLayout() {
        page.navigate("https://example.com");
        Locator header = page.locator("h1");
        assertTrue(header.isVisible());
    }

}
