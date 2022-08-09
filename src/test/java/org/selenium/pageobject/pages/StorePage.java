package org.selenium.pageobject.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.selenium.pageobject.base.BasePage;

public class StorePage extends BasePage {
    private final By searchField = By.cssSelector("#woocommerce-product-search-field-0");
    private final By searchBtn = By.cssSelector("button[value='Search']");
    private final By searchTitle = By.cssSelector(".woocommerce-products-header__title.page-title");
    private final By viewCartBtn = By.cssSelector("a[title='View cart']");
    public final By noProductsFoundAlertText = By.cssSelector("p.woocommerce-info");

    public StorePage(WebDriver driver) {
        super(driver);
    }

    private StorePage enterTextInSearchField(String text){
        driver.findElement(searchField).sendKeys(text);
        return this;
    }

    private StorePage clickSearchBtn() {
        driver.findElement(searchBtn).click();
        return this;

    }

    public StorePage searchForProductWithPartialMatch(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
        enterTextInSearchField(text).clickSearchBtn();
        return this;
    }

    public ProductPage searchForProductWithExactMatch(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
        enterTextInSearchField(text).clickSearchBtn();
        return new ProductPage(driver);
    }

    public String getTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(searchTitle)).getText();
    }

    private By getAddToCartBtnElement(String productName) {
        return By.cssSelector("a[aria-label='Add “" + productName+ "” to your cart']");
    }

    public StorePage clickAddCartButton(String productName){
        By addToCartBtn = getAddToCartBtnElement(productName);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
        return this;
    }

    public CartPage clickViewCart() {
        wait.until(ExpectedConditions.elementToBeClickable(viewCartBtn)).click();
        return new CartPage(driver);
    }

    public boolean isSearchTitleContainsText(String searchText){
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(searchTitle,"Search results: “" + searchText + "”"));
    }

    public StorePage load() {
        load("/store");
        return this;
    }

    public boolean isNoProductsFoundTextDisplayed() {
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(noProductsFoundAlertText,
                "No products were found matching your selection."));
    }

}
