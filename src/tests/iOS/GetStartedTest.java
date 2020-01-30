package tests.iOS;

import lib.IOSTestCase;
import lib.uiOS.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends IOSTestCase {

    @Test
    public void testPathThroughWelcome(){
        WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);

        WelcomePageObject.waitForLearnLink();
        WelcomePageObject.clickNextButton();

        WelcomePageObject.waitForNewWaysToExploreText();
        WelcomePageObject.clickNextButton();

        WelcomePageObject.waitForAddOrEditPreferredLanguagesText();
        WelcomePageObject.clickNextButton();

        WelcomePageObject.waitLearnMoreAboutDataCollectedLink();
        WelcomePageObject.clickGetStartedButton();

    }
}
