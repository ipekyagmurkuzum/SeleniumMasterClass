package org.selenium.pageobject.pages;

import com.sun.nio.file.ExtendedOpenOption;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.selenium.pageobject.base.BasePage;
import org.selenium.pageobject.objects.BillingAddress;
import org.selenium.pageobject.objects.Credentials;

public class CheckoutPage extends BasePage {

    private final By firstNameField = By.id("billing_first_name");
    private final By lastNameField = By.id("billing_last_name");
    private final By streetAddressField = By.id("billing_address_1");
    private final By townCityField = By.id("billing_city");
    private final By postcodeField = By.id("billing_postcode");
    private final By emailField = By.id("billing_email");
    private final By placeOrderBtn = By.id("place_order");
    private final By notice = By.cssSelector(".woocommerce-notice.woocommerce-notice--success.woocommerce-thankyou-order-received");
    private final By clickToLoginBtn = By.cssSelector(".showlogin");
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginBtn = By.cssSelector("button[value='Login']");
    private final By overlay = By.cssSelector(".blockUI.blockOverlay");
    private final By countryDropdown = By.id("billing_country");

    private final By stateDropdown = By.id("billing_state");
    private final By directBankTransferRadioButton = By.id("payment_method_bacs");
    private final By alternateCountryDropdown = By.id("select2-billing_country-container");
    private final By alternateStateDropdown = By.id("select2-billing_state-container");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutPage enterFirstName(String firstName) {
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
        return this;
    }
    public CheckoutPage enterLastName(String lastName) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
        return this;
    }
    public CheckoutPage enterAddress(String address) {
        driver.findElement(streetAddressField).clear();
        driver.findElement(streetAddressField).sendKeys(address);
        return this;
    }
    public CheckoutPage enterCity(String city) {
        driver.findElement(townCityField).clear();
        driver.findElement(townCityField).sendKeys(city);
        return this;
    }
    public CheckoutPage enterPostalCode(String postalCode) {
        driver.findElement(postcodeField).clear();
        driver.findElement(postcodeField).sendKeys(postalCode);
        return this;
    }
    public CheckoutPage enterEmail(String email) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        return this;
    }
    public CheckoutPage enterBillingInformation(BillingAddress billingAddress) {
        return enterFirstName(billingAddress.getFirstName())
                .enterLastName(billingAddress.getLastName())
                .selectCountry(billingAddress.getCountry())
                .enterAddress(billingAddress.getAddressLineOne())
                .enterCity(billingAddress.getCity())
                .selectState(billingAddress.getState())
                .enterPostalCode(billingAddress.getPostalCode())
                .enterEmail(billingAddress.getEmail());
    }

    public CheckoutPage placeOrder() {
        waitForOverlaysToDisappear(overlay);
        driver.findElement(placeOrderBtn).click();
        return this;
    }
    private CheckoutPage clickLoginLink() {
        driver.findElement(clickToLoginBtn).click();
        return this;
    }

    private CheckoutPage enterCredentials(Credentials credentials) {
        driver.findElement(usernameField).sendKeys(credentials.getUsername());
        driver.findElement(passwordField).sendKeys(credentials.getPassword());
        return this;
    }


    private CheckoutPage clickLoginBtn() {
        driver.findElement(loginBtn).click();
        return this;
    }

    public CheckoutPage login(Credentials credentials){
        clickLoginLink();
        enterCredentials(credentials);
        clickLoginBtn();
        return this;
    }

    public boolean isNoticeCorrect(String expectedNotice){
        return isTextCorrect(notice,expectedNotice);
    }

    public CheckoutPage selectCountry(String countryName) {
//        Select select = new Select(wait.until(ExpectedConditions.elementToBeClickable(countryDropdown)));
//        select.selectByVisibleText(countryName);

        // In firefox, dropdown cannot be scrolled into view, causing errors. Instead, use this.
        wait.until(ExpectedConditions.elementToBeClickable(alternateCountryDropdown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()='" + countryName + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",e);
        e.click();
        return this;
    }

    public CheckoutPage selectState(String stateName) {
//        Select select = new Select(driver.findElement(stateDropdown));
//        select.selectByVisibleText(stateName);

        // In firefox, dropdown cannot be scrolled into view, causing errors. Instead, use this.
        wait.until(ExpectedConditions.elementToBeClickable(alternateStateDropdown)).click();
        WebElement e = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()='" + stateName + "']")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",e);
        e.click();
        return this;
    }

    public CheckoutPage selectDirectBankTransfer() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(directBankTransferRadioButton));
        if (!element.isSelected()) {
            element.click();
        }
        return this;
    }

}
