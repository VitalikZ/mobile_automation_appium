package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

import static io.appium.java_client.touch.offset.PointOption.point;

public class MainPageObject {

    protected AppiumDriver driver;

    public MainPageObject(AppiumDriver driver){
        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String errorMessage, long timeOutInSeconds) {
        By by = this.getLocatorString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by));
    }

    public WebElement waitForElementPresent(String locator, String errorMessage) {
        return waitForElementPresent(locator, errorMessage, 5);
    }

    public WebElement waitForElementAndClick(String locator, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.click();
        return element;
    }

    public WebElement waitForElementAndSendKeys(String locator, String value, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String errorMessage, long timeOutInSeconds) {
        By by = this.getLocatorString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public WebElement waitForElementAndClear(String locator, String errorMessage, long timeOutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeOutInSeconds);
        element.clear();
        return element;
    }

    public WebElement checkInputBeforeSearch(WebElement element, String textOfElement) {
        textOfElement = element.getAttribute("text");
        Assert.assertTrue("Element's text is not as expected!", textOfElement.equals("Search…"));
        return element;
    }

    public void swipeUp(int timeOfSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        action
                .press(point(x, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMinutes(timeOfSwipe)))
                .moveTo(point(x, endY))
                .release()
                .perform();

    }

    public void swipeUpQuick() {
        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String errorMessage, int maxSwipes) {
        By by = this.getLocatorString(locator);
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (alreadySwiped > maxSwipes) {
                waitForElementPresent(locator, "Can't find element by swiping up. \n" + errorMessage, 0);
                return;
            }
            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeElementToLeft(String locator, String errorMessage) {
        WebElement element = waitForElementPresent(locator, errorMessage, 10);
        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(point(rightX,middleY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(300)))
                .moveTo(point(leftX,middleY))
                .release()
                .perform();
    }

    public int getAmountOfELements(String locator) {
        By by = this.getLocatorString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfELements(locator);
        if (amountOfElements > 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public void assertElementPresent(String locator, String errorMessage) {
        int amountOfElements = getAmountOfELements(locator);
        if (amountOfElements != 0) {
            String defaultMessage = "An element '" + locator + "' supposed to be present =)";
            throw new AssertionError(defaultMessage + " " + errorMessage);
        }
    }

    public String waitForElementAndGetAttribute(String locator, String attribute, String errorMessage, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(locator, errorMessage, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void swipeToElement(String locator){
        this.swipeUpToFindElement(locator, "error", 5);
    }

    private By getLocatorString(String locatorWithType) {
        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        if (byType.equals("xpath")) {
            return By.xpath(locator);
        } else if (byType.equals("id")) {
            return By.id(locator);
        } else {
            throw new IllegalArgumentException("Cannot get type of locator. Locator: " + locatorWithType);
        }
    }
}
