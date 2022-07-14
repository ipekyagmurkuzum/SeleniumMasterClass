package org.selenium.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pageobject.base.BasePage;

public class CartPage extends BasePage {

    private final By productName = By.cssSelector("td[class='product-name'] a");
//  private final By checkoutBtn = By.cssSelector(".checkout-button.button.alt.wc-forward");

    @FindBy(how =  How.CSS, using = ".checkout-button.button.alt.wc-forward") @CacheLookup private WebElement checkoutBtn;
    public CartPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    public boolean isProductNameCorrect(String expectedProductName) {
        return isTextCorrect(productName,expectedProductName);
    }
    public CheckoutPage checkout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
        return new CheckoutPage(driver);
    }
}
