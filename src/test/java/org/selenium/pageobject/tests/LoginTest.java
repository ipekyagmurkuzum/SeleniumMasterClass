package org.selenium.pageobject.tests;


import org.selenium.pageobject.api.actions.CartApi;
import org.selenium.pageobject.api.actions.SignUpApi;
import org.selenium.pageobject.base.BaseTest;
import org.selenium.pageobject.objects.BillingAddress;
import org.selenium.pageobject.objects.Product;
import org.selenium.pageobject.objects.User;
import org.selenium.pageobject.pages.CheckoutPage;
import org.selenium.pageobject.utils.FakerUtils;
import org.selenium.pageobject.utils.JacksonUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends BaseTest {

    @Test
    public void loginDuringCheckout() throws IOException {
        String username = "demouser" + new FakerUtils().generateRandomNumber();
        User user = new User()
                .setUsername(username)
                .setPassword("demouserpwd")
                .setEmail(username + "@yagmur.com");
        BillingAddress billingAddress = JacksonUtils.deserializeJson("billingAddress.json", BillingAddress.class);

        SignUpApi signUpApi = new SignUpApi();
        signUpApi.register(user);

        CartApi cartApi = new CartApi();
        Product product = new Product(1215);
        cartApi.addToCart(product.getId(), 1);

        CheckoutPage checkoutPage = new CheckoutPage(getDriver()).load();
        injectCookiesToBrowser(cartApi.getCookies());
        checkoutPage.load();
        checkoutPage.login(user);
        Assert.assertFalse(checkoutPage.isClickToLoginDisplayed());
        Assert.assertTrue(checkoutPage.getProductName().contains(product.getName()));
    }
}
