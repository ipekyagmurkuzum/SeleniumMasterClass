package org.selenium.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.selenium.pageobject.base.BasePage;

public class HomePage extends BasePage {

    private By storeMenuLink = By.cssSelector("#menu-item-1227 > a");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public StorePage navigateToStoreUsingMenu(){
        driver.findElement(storeMenuLink).click();
        return new StorePage(driver);
    }

    public HomePage load(){
        load("/");
        return this;
    }
}
