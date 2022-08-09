package org.selenium.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pageobject.base.BasePage;
import org.selenium.pageobject.objects.User;

public class AccountPage extends BasePage {

    private final By usernameFld = By.cssSelector("#username");
    private final By passwordFld = By.cssSelector("#password");
    private final By loginBtn = By.name("login");
    private final By errorFld = By.cssSelector("ul.woocommerce-error li");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage login(User user) {
        wait.until(ExpectedConditions.elementToBeClickable(usernameFld)).sendKeys(user.getUsername());
        driver.findElement(passwordFld).sendKeys(user.getPassword());
        driver.findElement(loginBtn).click();
        return this;
    }

    public boolean isErrorMessageDisplayed(String username) {
        return wait.until(ExpectedConditions.textToBe(errorFld, "Error: The username "
                + username
                + " is not registered on this site. If you are unsure of your username, try your email address instead."));
    }
}
