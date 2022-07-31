package org.selenium.pageobject.tests;

import org.selenium.pageobject.base.BaseTest;
import org.selenium.pageobject.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @Test
    public void searchWithPartialMatch() {
        String searchText = "blue";
        StorePage storePage = new StorePage(getDriver())
                .load()
                .searchForProduct(searchText);
        Assert.assertTrue(storePage.isSearchTitleCorrect(searchText));
    }
}
