package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListsPageObject extends MainPageObject {

    public static final String
            FOLDER_BY_NAME_TEMPLATE = "xpath://*[@text='{FOLDER_NAME}']",
            ARTICLE_BY_TITLE_TPL = "xpath://*[@text='{TITLE}']";

    private static String getFolderXpathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TEMPLATE.replace("{FOLDER_NAME}", name_of_folder);
    }

    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }

    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openFolderByName(String name_of_folder) {
        String folder_name_xpath = getFolderXpathByName(name_of_folder);
        this.waitForElementAndClick(folder_name_xpath,"Can't find folder by name" + name_of_folder,5);
    }

    public void swipeByArticleToDelete(String article_title) {
        this.waitForArticleToAppearByTitle(article_title);
        String article_title_xpath = getFolderXpathByName(article_title);
        this.swipeElementToLeft(article_title_xpath,"Can't find save article");
        waitForArticleToDisappearByTitle(article_title);
    }

    public void waitForArticleToAppearByTitle(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementPresent(article_title,"Can't find saved article by title" + article_title,5);
    }

    public void waitForArticleToDisappearByTitle(String article_title){
        String article_xpath = getFolderXpathByName(article_title);
        this.waitForElementNotPresent(article_title,"Can't delete saved article" + article_title,5);
    }
}
