package com.iggy.utils;

import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public class ScreenshotOnFailureExtension
        implements TestExecutionExceptionHandler {

    private final Supplier<WebDriver> driverSupplier;

    public ScreenshotOnFailureExtension(Supplier<WebDriver> driverSupplier) {
        this.driverSupplier = driverSupplier;
    }

    @Override
    public void handleTestExecutionException(
            ExtensionContext context, Throwable throwable) throws Throwable {

        WebDriver driver = driverSupplier.get();
        if (driver != null) {
            String testName = context.getDisplayName()
                    .replaceAll("[^a-zA-Z0-9_-]", "_");
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = "screenshots/" + testName + "_" + timestamp + ".png";

            try {
                File screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.FILE);
                Files.createDirectories(Paths.get("screenshots"));
                Files.copy(screenshot.toPath(), Paths.get(fileName));
                System.out.println("✅ Screenshot saved: " + fileName);
            } catch (IOException e) {
                System.err.println("Screenshot failed: " + e.getMessage());
            }
        }
        throw throwable;
    }
}