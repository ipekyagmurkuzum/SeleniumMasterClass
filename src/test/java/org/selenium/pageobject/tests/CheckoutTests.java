package org.selenium.pageobject.tests;

import org.selenium.pageobject.base.BaseTest;
import org.selenium.pageobject.objects.Credentials;
import org.selenium.pageobject.objects.BillingAddress;
import org.selenium.pageobject.objects.Product;
import org.selenium.pageobject.pages.CartPage;
import org.selenium.pageobject.pages.CheckoutPage;
import org.selenium.pageobject.pages.HomePage;
import org.selenium.pageobject.pages.StorePage;
import org.selenium.pageobject.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTests extends BaseTest {
    String searchText = "blue";

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("billingAddress.json", BillingAddress.class);
        Product product = new Product(1215);

        StorePage storePage = new HomePage(driver)
                .load()
                .navigateToStoreUsingMenu()
                .searchForProduct(searchText);
        Assert.assertTrue(storePage.isSearchTitleCorrect(searchText));

        storePage.clickAddCartButton(product.getName());
        CartPage cartPage = storePage.clickViewCart();
        Assert.assertEquals(cartPage.getProductName(), product.getName());

        CheckoutPage checkoutPage = cartPage
                .checkout()
                .enterBillingInformation(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertTrue(checkoutPage.isNoticeCorrect( "Thank you. Your order has been received."));
    }

    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("billingAddress.json", BillingAddress.class);
        Product product = new Product(1215);
        Credentials credentials = JacksonUtils.deserializeJson("credentials.json", Credentials.class);

        StorePage storePage = new HomePage(driver)
                .load()
                .navigateToStoreUsingMenu()
                .searchForProduct(searchText);
        Assert.assertTrue(storePage.isSearchTitleCorrect(searchText));

        storePage.clickAddCartButton(product.getName());
        CartPage cartPage = storePage.clickViewCart();
        Assert.assertTrue(cartPage.isProductNameCorrect(product.getName()));

        CheckoutPage checkoutPage = cartPage
                .checkout()
                .login(credentials)
                .enterBillingInformation(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertTrue(checkoutPage.isNoticeCorrect( "Thank you. Your order has been received."));
    }
}
