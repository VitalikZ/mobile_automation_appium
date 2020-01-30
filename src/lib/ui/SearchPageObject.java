package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPageObject  extends MainPageObject{

    private static final String
            SEARCH_INIT_ELEMENT = "xpath://*[contains(@text, 'Search Wikipedia')]",
            SEARCH_INPUT = "xpath://*[contains(@text, 'Search…')]",
            SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn",
            SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='{SUBSTRING}']",
            SEARCH_RESULT_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']",
            SEARCH_EMPTY_RESULT_LABEL = "xpath://*[@text='No results found']",
            SEARCH_TEXT = "id:org.wikipedia:id/search_src_text";

    public SearchPageObject(AppiumDriver driver) {
        super(driver);
    }

    //TEMPLATES METHODS
    public static String getResultSearchElement(String substring){
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }
    //TEMPLATES METHODS

    public void initSearchInput(){
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element");
    }

    public void waitForCancelButtonToAppear(){
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public void waitForCancelButtonToDisappear(){
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 5);
    }

    public void clickCancelSearch(){
        waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 5);
    }

    public void typeSearchLine(String search_line){
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot find and type search input", 10);
    }

    public void waitForSearchResult(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result" + substring, 10);
    }

    public void clickByArticleWithSubstring(String substring){
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring" + substring, 5);
    }

    public int getAmountOfFoundArticle(){
        this.waitForElementPresent(SEARCH_RESULT_ELEMENT, "Can't find anything by request ", 15);

        return this.getAmountOfELements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultLabel(){

        this.waitForElementPresent(SEARCH_EMPTY_RESULT_LABEL, "Cannot find empty result element", 15);
    }

    public void assertThereIsNoResultPage(){
        assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposted not find any results");
    }

    public void assertThereIsSomethingOnTheResultPage(){
        assertElementPresent(SEARCH_RESULT_ELEMENT, "Hmm, but we shouldn't see some articles");
    }

    public void checkTextIsDisplayedInSearchField(){

        this.waitForElementPresent(SEARCH_TEXT, "Cannot find text in search input", 15);
    }

    public void clickCloseSearchButton(){
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 5);
    }

    public boolean checkTheDefaultTextOfSearcInput(){
        WebElement element = this.waitForElementPresent("org.wikipedia:id/search_src_text", "Search input is not appeared", 15);

        String textOfElement = element.getAttribute("text");
        Assert.assertTrue("Element's text is not as expected!", textOfElement.equals("Search…"));
        return true;
    }

    public String getTextFromSearchField(){
        WebElement secondElement = this.waitForElementPresent("org.wikipedia:id/search_src_text", "Search input is not appeared", 15);
        String textOfElement2 = secondElement.getAttribute("text");
        return textOfElement2;
    }

    public boolean foundArticlesOnSearchResultPage(String searchQuery) {
        List<WebElement> articles = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));
        for (WebElement article : articles) {
            if (article.getText().trim().equals(searchQuery))
                return true;
        }
        return false;
    }

}
