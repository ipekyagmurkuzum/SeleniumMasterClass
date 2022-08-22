package org.selenium.pageobject.tests;

import org.selenium.pageobject.api.actions.CartApi;
import org.selenium.pageobject.api.actions.SignUpApi;
import org.selenium.pageobject.base.BaseTest;
import org.selenium.pageobject.objects.User;
import org.selenium.pageobject.objects.BillingAddress;
import org.selenium.pageobject.objects.Product;
import org.selenium.pageobject.pages.CartPage;
import org.selenium.pageobject.pages.CheckoutPage;
import org.selenium.pageobject.pages.HomePage;
import org.selenium.pageobject.pages.StorePage;
import org.selenium.pageobject.utils.ConfigLoader;
import org.selenium.pageobject.utils.FakerUtils;
import org.selenium.pageobject.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class CheckoutTest extends BaseTest {
    String searchText = "blue";

    @Test
    public void guestCheckoutUsingDirectBankTransfer() throws IOException {
        BillingAddress billingAddress = JacksonUtils.deserializeJson("billingAddress.json", BillingAddress.class);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver())
                .load();
        CartApi cartApi = new CartApi();

        cartApi.addToCart(1215, 1);
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.load()
                .enterBillingInformation(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertTrue(checkoutPage.isNoticeCorrect("Thank you. Your order has been received."));
    }

    @Test
    public void loginAndCheckoutUsingDirectBankTransfer() throws IOException, InterruptedException {
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User()
                .setUsername(username)
                .setPassword("demouserpwd")
                .setEmail(username + "@yagmur.com");
        BillingAddress billingAddress = JacksonUtils.deserializeJson("billingAddress.json", BillingAddress.class);

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi(signUpApi.getCookies());
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(signUpApi.getCookies());
        checkoutPage.load();
        Thread.sleep(4000);
        Assert.assertFalse(checkoutPage.isClickToLoginDisplayed());
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
        checkoutPage
                .enterBillingInformation(billingAddress)
                .selectDirectBankTransfer()
                .placeOrder();
        Assert.assertTrue(checkoutPage.isNoticeCorrect("Thank you. Your order has been received."));
    }

    @Test
    public void guestCheckoutUsingCashOnDelivery() {

    }

    @Test
    public void loginAndCheckoutUsingCashOnDelivery() {

    }
}
