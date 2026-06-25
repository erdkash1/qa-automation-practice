package com.iggy.selenium.theinternet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CheckboxesPage {

    private WebDriver driver;
    public CheckboxesPage(WebDriver driver){
        this.driver = driver;
    }

    private By checkboxes = By.cssSelector("#checkboxes input[type='checkbox']");
    public boolean isCheckboxChecked(int index){
        List<WebElement> checkboxList = driver.findElements(checkboxes);
        return checkboxList.get(index).isSelected();
    }

    public void clickCheckbox(int index){
        List<WebElement> checkboxList = driver.findElements(checkboxes);
        checkboxList.get(index).click();
    }

}
