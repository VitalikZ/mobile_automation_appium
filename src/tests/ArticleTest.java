package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Assert;
import org.junit.Test;

public class ArticleTest extends CoreTestCase {

    @Test
    public void testCompareArticleTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.clickByArticleWithSubstring("Object-oriented programming language");
        String article_title= ArticlePageObject.getArticleTitle();

        Assert.assertEquals("We see unexpected title", "Java (programming language)", article_title);
    }

    @Test
    public void testSwipeArticle(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }

    @Test
    public void testSaveTwoArticles() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");

        ArticlePageObject.addArticleToMyList("Learning programing");
        String name_of_folder = "Learning programing";
        SearchPageObject.clickCloseSearchButton();
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Python");
        ArticlePageObject.addArticleToMyList("Learning programing");
        ArticlePageObject.deleteArticleByTitleArticle("Java (programming language)");
        ArticlePageObject.waitForTitleElement();
        String titleInReadingList = ArticlePageObject.getArticleTitle();
        ArticlePageObject.waitForTitleElement();
        String titleOnArticlePage = ArticlePageObject.getArticleTitle();
        Assert.assertEquals("We see unexpected title", titleOnArticlePage, titleInReadingList);
    }

    @Test
    public void testAssertTitle() {
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("2342342342342342wefsdfsdfsdf");
        ArticlePageObject.waitForTitleElement();
    }
}
