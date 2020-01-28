package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MainPageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");

    }

    @Test
    public void testCancelSearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testAmountOfNotEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "Linkin Park discography";
        SearchPageObject.typeSearchLine(search_line);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticle();

        Assert.assertTrue("We found more result than one!", amount_of_search_results > 0);
    }

    @Test
    public void testAmountForEmptySearch() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String search_line = "asdfghjkl12345678";
        SearchPageObject.typeSearchLine(search_line);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNoResultPage();
    }

    @Test
    public void testClearSearchInput() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.checkTextIsDisplayedInSearchField();
        SearchPageObject.clickCloseSearchButton();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testCheckTheTextInSearchInput() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.checkTheDefaultTextOfSearcInput();
        SearchPageObject.typeSearchLine("Java");
        Assert.assertTrue("Element's text is not as expected!", SearchPageObject.getTextFromSearchField().equals("Java"));
    }

    @Test
    public void testCancelSearchAndCheckArticleIsAbsent() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.assertThereIsSomethingOnTheResultPage();
        Assert.assertTrue(SearchPageObject.foundArticlesOnSearchResultPage("Java"));
    }

    @Test
    public void testFindElementAfterSwiping() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        MainPageObject MainPageObject = new MainPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.foundArticlesOnSearchResultPage("Java");
        ArticlePageObject.waitForTitleElement();
        MainPageObject.swipeToElementByXpath("//*[@text='View page in browser1']");

    }
}
