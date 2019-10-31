import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","9");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/vitaliizakorchevnyy/Desktop/JavaAppiumAuto/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void firstTest() throws InterruptedException {
        waitForElementAndClick(
                "//*[contains(@text, 'Search Wikipedia')]",
                "Cannot find search input",
                5);

        waitForElementAndSendKeys(
                "//*[contains(@text, 'Search…')]",
                "Java",
                "Cannot find search input",
                5);

        waitForElementPresent(
                "//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']",
                "Cannot find 'Object-oriented programming language'",
                15
        );

        //Thread.sleep(10000);
    }

    @Test
    public void testCancelSearch() {
        waitForElementByIdAndClick(
                "org.wikipedia:id/search_container",
                "Can't find 'Search Wikipedia' input",
                5);

        waitForElementByIdAndClick(
                "org.wikipedia:id/search_close_btn",
                "Can't find 'X' to cancel search",
                5);

        waitForElementNotPresent(
                "org.wikipedia:id/search_close_btn",
                "Element X is still present",
                5);

    }

    private WebElement waitForElementPresent(By by, String error_message, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private WebElement waitForElementPresentById(String id, String error_message, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        By by = By.id(id);
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementByIdAndClick(String id, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresentById(id, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementByIdAndSendKeys(String id, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresentById(id, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(String id, String error_message, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        By by = By.id(id);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
}
