package org.selenium.pageobject.tests;

import org.selenium.pageobject.base.BaseTest;
import org.selenium.pageobject.pages.ProductPage;
import org.selenium.pageobject.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @Test
    public void searchWithPartialMatch() {
        String searchText = "blue";
        StorePage storePage = new StorePage(getDriver())
                .load()
                .searchForProductWithPartialMatch(searchText);
        Assert.assertTrue(storePage.isSearchTitleContainsText(searchText));
    }

    @Test
    public void searchWithExactMatch() {
        String searchText = "blue shoes";
        ProductPage productPage = new StorePage(getDriver())
                .load()
                .searchForProductWithExactMatch(searchText);
        Assert.assertTrue(productPage.isUrlCorrect("/product/blue-shoes/"));
    }

    @Test
    public void searchNonExistingProduct() {
        String searchText = "blueshoes";
        StorePage storePage = new StorePage(getDriver())
                .load()
                .searchForProductWithPartialMatch(searchText);
        Assert.assertTrue(storePage.isNoProductsFoundTextDisplayed());
    }
}
