package org.selenium.pageobject.base;

import org.openqa.selenium.WebDriver;
import org.selenium.pageobject.factory.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {
    private ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private void setDriver(WebDriver driver) {
        this.driver.set(driver);
    }

    protected WebDriver getDriver(){
        return this.driver.get();
    }
    @Parameters("browser")
    @BeforeMethod
    public void startDriver(@Optional String browser) {
        browser = System.getProperty("browser",browser);
        if (browser==null) browser="CHROME";
        setDriver(new DriverManager().initializeDriver(browser));
        System.out.println("Current thread: " + Thread.currentThread().getId() +
                "Driver: " + getDriver());
    }

    @AfterMethod
    public void quitDriver() {
        getDriver().quit();
    }
}
