package com.iggy.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.Geolocation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import com.microsoft.playwright.options.ColorScheme;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.SAME_THREAD)
public class MobileEmulationTests {

    private static Playwright playwright;
    private Page page;
    private Browser browser;

    @BeforeAll
    static void setUp() {
        playwright = Playwright.create();
    }

    @AfterAll
    static void tearDown() {
        playwright.close();
    }

    @AfterEach
    void tearDownBrowser() {
        browser.close();
    }

    @Test
    void shouldLoginOnIPhone13() {
        BrowserContext context = playwright.chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(true))
                .newContext(new Browser.NewContextOptions()
                        .setViewportSize(390, 844)
                        .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1")
                        .setIsMobile(true)
                        .setHasTouch(true)
                );

        browser = context.browser();
        page = context.newPage();

        page.navigate("https://www.saucedemo.com");
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("mobile-screenshots/iphone13-login.png")));

        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");

        assertThat(page).hasURL(
                java.util.regex.Pattern.compile(".*inventory.*"));

        System.out.println(" iPhone 13 test passed!");
        System.out.println("   Viewport: 390x844");
    }

    @Test
    void shouldLoginOnPixel5Android() {
        BrowserContext context = playwright.chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(true))
                .newContext(new Browser.NewContextOptions()
                        .setViewportSize(393, 851)
                        .setUserAgent("Mozilla/5.0 (Linux; Android 11; Pixel 5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.91 Mobile Safari/537.36")
                        .setIsMobile(true)
                        .setHasTouch(true)
                );

        browser = context.browser();
        page = context.newPage();

        page.navigate("https://www.saucedemo.com");

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("mobile-screenshots/pixel5-login.png")));

        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");

        assertThat(page.locator("[data-test='title']"))
                .hasText("Products");

        System.out.println(" Pixel 5 Android test passed!");
        System.out.println("   Viewport: 393x851");
    }

    @Test
    void shouldLoginOnIPad() {
        BrowserContext context = playwright.chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(true))
                .newContext(new Browser.NewContextOptions()
                        .setViewportSize(768, 1024)
                        .setUserAgent("Mozilla/5.0 (iPad; CPU OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.0 Mobile/15E148 Safari/604.1")
                        .setIsMobile(true)
                        .setHasTouch(true)
                );

        browser = context.browser();
        page = context.newPage();

        page.navigate("https://www.saucedemo.com");

        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("mobile-screenshots/ipad-login.png")));

        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");

        assertThat(page).hasURL(
                java.util.regex.Pattern.compile(".*inventory.*"));

        System.out.println(" iPad test passed!");
        System.out.println("   Viewport: 768x1024");
    }

    @Test
    void shouldVerifyMobileViewport() {
        BrowserContext context = playwright.chromium().launch(
                        new BrowserType.LaunchOptions().setHeadless(true))
                .newContext(new Browser.NewContextOptions()
                        .setViewportSize(390, 844)
                        .setIsMobile(true)
                        .setHasTouch(true)
                );

        browser = context.browser();
        page = context.newPage();
        page.navigate("https://www.saucedemo.com");

        int width = ((Number) page.evaluate("() => window.innerWidth")).intValue();
        int height = ((Number) page.evaluate("() => window.innerHeight")).intValue();

        System.out.println(" Mobile viewport: " + width + "x" + height);

        assertEquals(390, width, "Width should be 390px (iPhone 13)");
        assertTrue(height > 0, "Height should be positive");
    }
    @Test
    void shouldOpenHamburgerMenuOnMobile() {
        BrowserContext context = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true))
                .newContext(new Browser.NewContextOptions()
                        .setViewportSize(390, 844)
                        .setIsMobile(true)
                        .setHasTouch(true)
                );

        browser = context.browser();
        page = context.newPage();
        page.navigate("https://www.saucedemo.com");

        page.fill("[data-test='username']", "standard_user");
        page.fill("[data-test='password']", "secret_sauce");
        page.click("[data-test='login-button']");

        page.click("#react-burger-menu-btn");

        Locator logoutLink = page.locator("#logout_sidebar_link");
        assertThat(logoutLink).isVisible();

        System.out.println(" Mobile hamburger menu test passed!");
    }


    @Test
    void shouldEmulateMobileGeolocation() {
        BrowserContext context = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true))
                .newContext(new Browser.NewContextOptions()
                        .setViewportSize(390, 844)
                        .setIsMobile(true)
                        .setGeolocation(new Geolocation(48.8584, 2.2945))
                        .setPermissions(Arrays.asList("geolocation"))
                );

        browser = context.browser();
        page = context.newPage();
    }


    @Test
    void shouldLoadWithDarkModeEmulation() {
        BrowserContext context = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true))
                .newContext(new Browser.NewContextOptions()
                        .setViewportSize(390, 844)
                        .setIsMobile(true)
                        .setColorScheme(ColorScheme.DARK)
                );

        browser = context.browser();
        page = context.newPage();
        page.navigate("https://www.saucedemo.com");
        page.screenshot(new Page.ScreenshotOptions()
                .setPath(Paths.get("mobile-screenshots/dark-mode.png")));
    }
}