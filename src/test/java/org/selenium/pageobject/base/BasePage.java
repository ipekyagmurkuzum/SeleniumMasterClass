package org.selenium.pageobject.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium.pageobject.utils.ConfigLoader;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    public WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        waitLong = new WebDriverWait(driver,Duration.ofSeconds(15));
//        waitShort = new WebDriverWait(driver,Duration.ofSeconds(5));
    }

    public void load(String endPoint) {
        driver.get(ConfigLoader.getInstance().getBaseUrl() + endPoint);
    }

    public void waitForOverlaysToDisappear(By overlay) {
        List<WebElement> overlays = driver.findElements(overlay);
        System.out.println(overlays.size());
        if (overlays.size() > 0) {
            wait.until(ExpectedConditions
                    .invisibilityOfAllElements(overlays));
        }
    }

    public boolean isTextCorrect(By locator, String expectedText) {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator,expectedText));
    }
}
