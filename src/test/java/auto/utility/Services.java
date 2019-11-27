package auto.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

import static org.apache.log4j.Logger.getLogger;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;


public class Services {

    protected WebDriver driver;

    public Services(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForElement(String locator) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    protected void click(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    protected void type(String locator, String text) {
        driver.findElement(By.xpath(locator)).sendKeys(text);
    }

    protected void waitForElementVisible(String locator) {
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }


}
