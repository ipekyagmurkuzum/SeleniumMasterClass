package org.selenium.pageobject.tests;

import org.selenium.pageobject.base.BaseTest;
import org.selenium.pageobject.pages.HomePage;
import org.selenium.pageobject.pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;



public class NavigationTest extends BaseTest {
    @Test
    public void navigateFromHomeToStoreUsingMainMenu() {
        StorePage storePage = new HomePage(getDriver())
                .load()
                .navigateToStoreUsingMenu();
        Assert.assertEquals(storePage.getTitle(),"Store");
    }

    @Test
    public void navigateFromStoreToProductPage() {

    }

    @Test
    public void navigateFromHomeToFeaturedProductPage() {

    }
}
