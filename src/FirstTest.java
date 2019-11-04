import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "9");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "/Users/vitaliizakorchevnyy/Desktop/JavaAppiumAuto/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() throws InterruptedException {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language'",
                15);

        //Thread.sleep(10000);
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can't find 'Search Wikipedia' input",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find 'X' to cancel search",
                5);

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element X is still present",
                5);

    }

    @Test
    public void testClearSearchInput() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Can't find 'Search Wikipedia' input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot find search field",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Can't find 'X' to cancel search",
                5);

        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "Element X is still present",
                5);
    }

    @Test
    public void testCompareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Java article",
                5);

        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15);

        String article_title = title_element.getAttribute("text");
        Assert.assertEquals("We see unexpected title", "Java (programming language)", article_title);

    }

    @Test
    public void testCheckTheTextInSearchInput() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                5);

        WebElement element = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Search input is not appeared",
                15);

        String textOfElement = element.getAttribute("text");
        Assert.assertTrue("Element's text is not as expected!", textOfElement.equals("Search…"));

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        WebElement secondElement = waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Search input is not appeared",
                15);

        String textOfElement2 = secondElement.getAttribute("text");
        Assert.assertTrue("Element's text is not as expected!", textOfElement2.equals("Java"));
    }

    @Test
    public void testCancelSearchAndCheckArticleIsAbsent() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "Cannot find list of search results",
                15);

        Assert.assertTrue(foundArticlesOnSearchResultPage("Java"));
    }

    @Test
    public void testFindElementAfterSwiping() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Appium",
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "Cannot find Appium in search",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15);

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser1']"),
                "Can't find the end of the article",
                20);
    }

    @Test
    public void saveFirstArticleToMyList(){
        waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find search input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Java",
                "Cannot find search input",
                5);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Java article",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Can't find article title",
                15);

        //////////////////////////////////////
//        waitForElementAndClick(
//                By.xpath("//android.widget.ImageButton[@*='Navigate up']"),
//                //By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']']"),
//                "Can't close article, can't find X button",
//                8);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Can't find button to open article options",
                8);

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Can't find option to add article to reading list",
                5);

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Can't find option 'Got it' tip overlay",
                5);

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Can't find input to set name of article folder",
                5);

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "Learning programing",
                "Can't put text into article folder input'",
                5);

        waitForElementAndClick(
                By.id("android:id/button1"),
                "Can't click 'OK' button",
                5);

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@*='Navigate up']"),
                "Can't close article, can't find X button",
                8);

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Can't press navigation 'My list' button",
                5);

        waitForElementAndClick(
                By.xpath("//*[@text='Learning programming']"),
                "Can't find created folder",
                5);

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can't find save article");

        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Can't delete saved article",
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

    private boolean waitForElementNotPresent(By by, String error_message, long timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeOutInSeconds){
        WebElement element = waitForElementPresent(by,error_message, timeOutInSeconds);
        element.clear();
        return element;
    }

    private WebElement checkInputBeforeSearch(WebElement element, String textOfElement) {
        textOfElement = element.getAttribute("text");
        Assert.assertTrue("Element's text is not as expected!", textOfElement.equals("Search…"));
        return element;
    }

    protected void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action
                .press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();

    }

    public boolean foundArticlesOnSearchResultPage(String searchQuery)
    {
        List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        for (WebElement article : articles)
        {
            if (article.getText().trim().equals(searchQuery))
                return true;
        }
        return false;
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes){
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0){
            if(already_swiped > max_swipes){
                waitForElementPresent(by, "Can't find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresent(by, error_message, 10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(150)
                .moveTo(left_x, middle_y)
                .release()
                .perform();

    }
}
