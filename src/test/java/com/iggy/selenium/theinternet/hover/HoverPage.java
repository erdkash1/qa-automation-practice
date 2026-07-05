package com.iggy.selenium.theinternet.hover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class HoverPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HoverPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By userFigures = By.cssSelector(".figure");
    private By figcaption = By.cssSelector(".figcaption h5");


    public void hoverOverUser(int index) {
        WebElement figure = driver.findElements(userFigures).get(index);
        Actions actions = new Actions(driver);
        actions.moveToElement(figure).perform();
    }

    public String getHiddenText(int index){
        return driver.findElements(figcaption).get(index).getText();
    }
}
