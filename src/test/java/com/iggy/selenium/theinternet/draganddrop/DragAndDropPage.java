package com.iggy.selenium.theinternet.draganddrop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DragAndDropPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public DragAndDropPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By columnA = By.id("column-a");
    private By columnB = By.id("column-b");

    public void dragAtoB(){
        WebElement source = driver.findElement(columnA);
        WebElement target = driver.findElement(columnB);
        Actions actions = new Actions(driver);
        actions.clickAndHold(source)
                .moveToElement(target)
                .release()
                .perform();
    }
    public String getColumnAText(){
        return driver.findElement(columnA).getText();
    }

    public String getColumnBText(){
        return driver.findElement(columnB).getText();
    }

}
