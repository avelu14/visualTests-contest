package auto.testcases;


import auto.pages.AppPage;
import auto.utility.Init;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Eyes;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class VisualTestDynamic extends Init {

    private EyesRunner runner;
    private Eyes eyes;
    private static BatchInfo batch;

    @DataProvider
    public static Object[][] validCredentials() {
        return new Object[][]{{"testuser", "test123"}};
    }

    @BeforeClass
    public void beforeEach() {

        runner = new ClassicRunner();
        eyes = new Eyes(runner);
        eyes.setApiKey("ocM4edRc2mVjpqcnCSUe3NxQ1iI109xKzQosR102FaVizoo110");
        eyes.setMatchLevel(MatchLevel.LAYOUT);
        batch = new BatchInfo("Demo dynamic test");
        eyes.setBatch(batch);


    }

    @AfterClass
    public void afterEach() {
        eyes.abortIfNotClosed();

    }


    /*
        Dynamic Content Test
        This test is to validate existence of the dynamic ads
    */

    @Test(dataProvider = "validCredentials")
    public void testDynamicContent(String username, String password) throws Exception{

        AppPage appPage = new AppPage(driver);

        eyes.open(driver, "Demo App", "DynamicContent", new RectangleSize(800, 600));

        String oldUrl = driver.getCurrentUrl();
        String newUrl = oldUrl + "?showAd=true";
        driver.navigate().to(newUrl);
        appPage.loginAction(username, password);

        eyes.checkWindow("DynamicContent Window");
        eyes.closeAsync();

    }

}
