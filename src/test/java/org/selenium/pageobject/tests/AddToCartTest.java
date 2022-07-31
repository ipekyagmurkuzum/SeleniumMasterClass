package org.selenium.pageobject.tests;


import org.selenium.pageobject.base.BaseTest;
import org.selenium.pageobject.objects.Product;
import org.selenium.pageobject.pages.CartPage;
import org.selenium.pageobject.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddToCartTest extends BaseTest {

    @Test
    public void addToCartFromStorePage() throws IOException {
        Product product = new Product(1215);
        CartPage cartPage = new StorePage(getDriver())
                .load()
                .clickAddCartButton(product.getName())
                .clickViewCart();
        Assert.assertTrue(cartPage.isProductNameCorrect(product.getName()));
    }

}
