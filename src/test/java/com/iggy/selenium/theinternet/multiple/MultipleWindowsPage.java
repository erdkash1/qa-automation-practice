package com.iggy.selenium.theinternet.multiple;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MultipleWindowsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MultipleWindowsPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By clickHereLink = By.linkText("Click Here");

    public void clickLink(){
        driver.findElement(clickHereLink).click();
    }

    public void switchToNewWindow(String originalHandle) {
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
    }

    public String getPageTitle(){
        return driver.getTitle();
    }

    public void switchToOriginalWindow(String originalHandle){
        driver.switchTo().window(originalHandle);
    }


}
