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


public class VisualTest extends Init {

    private EyesRunner runner;
    private Eyes eyes;
    private static BatchInfo batch;

    @DataProvider
    public static Object[][] validCredentials() {
        return new Object[][]{{"testuser", "test123"}};
    }

    @DataProvider
    public static Object[][] noUsernamePassword() {
        return new Object[][]{{"", ""}};
    }

    @DataProvider
    public static Object[][] onlyUsername() {
        return new Object[][]{{"testuser", ""}};
    }

    @DataProvider
    public static Object[][] onlyPassword() {
        return new Object[][]{{"", "test123"}};
    }

    @BeforeClass
    public void beforeEach() {

        runner = new ClassicRunner();
        eyes = new Eyes(runner);
        eyes.setApiKey("ocM4edRc2mVjpqcnCSUe3NxQ1iI109xKzQosR102FaVizoo110");

        batch = new BatchInfo("Demo run");
        eyes.setBatch(batch);


    }

    @AfterClass
    public void afterEach() {
        eyes.abortIfNotClosed();

    }

    /*
          This test is to validate the Login Page UI Elements
    */
    @Test
    public void testLoginPage() {

        eyes.open(driver, "Demo App", "testLoginPage", new RectangleSize(800, 600));
        eyes.checkWindow("Login Window");
        eyes.closeAsync();

    }

    /*
        Data-Driven Test
        If you don’t enter the username and password and click the login button, it should throw an error
    */
    @Test(dataProvider = "noUsernamePassword")
    public void testLoginNoUsernamePassword(String username, String password) {

        AppPage appPage = new AppPage(driver);
        eyes.open(driver, "Demo App", "noUsername_noPassword", new RectangleSize(800, 600));

        appPage.loginAction(username, password);

        eyes.checkWindow("noUsernamePassword Window");
        eyes.closeAsync();
    }


    /*
       Data-Driven Test
       If you only enter the username and click the login button, it should throw an error
  */
    @Test(dataProvider = "onlyUsername")
    public void testLoginOnlyUsername(String username, String password) {

        AppPage appPage = new AppPage(driver);
        eyes.open(driver, "Demo App", "onlyUsername", new RectangleSize(800, 600));

        appPage.loginAction(username, password);

        eyes.checkWindow("onlyUsername Window");
        eyes.closeAsync();
    }


    /*
        Data-Driven Test
        If you only enter the password and click the login button, it should throw an error
    */
    @Test(dataProvider = "onlyPassword")
    public void testLoginOnlyPassword(String username, String password) {

        AppPage appPage = new AppPage(driver);
        eyes.open(driver, "Demo App", "onlyPassword", new RectangleSize(800, 600));

        appPage.loginAction(username, password);

        eyes.checkWindow("onlyPassword Window");
        eyes.closeAsync();
    }


    /*
         Data-Driven Test
         If you enter both username (any value) and password (any value), it should log you in.
    */
    @Test(dataProvider = "validCredentials")
    public void testValidLogin(String username, String password) {

        AppPage appPage = new AppPage(driver);
        eyes.open(driver, "Demo App", "validCredentials", new RectangleSize(800, 600));

        appPage.loginAction(username, password);

        eyes.checkWindow("validCredentials Window");
        eyes.closeAsync();
    }


    /*
         Table Sort Test
         This test should click on the "Amounts" header, and verify that the column is in ascending order
         and that each row’s data stayed in tact after the sorting.
    */
    @Test(dataProvider = "validCredentials")
    public void testTableSort(String username, String password) {

        AppPage appPage = new AppPage(driver);
        eyes.open(driver, "Demo App", "tableSort", new RectangleSize(800, 600));
        appPage.loginAction(username, password);

        eyes.setForceFullPageScreenshot(true);
        eyes.checkWindow("Before amount Sort Window");
        appPage.clickOnAmount();
        eyes.checkWindow("After amount Sort Window");
        eyes.closeAsync();

    }

    /*
       Canvas Chart Test
        This test is to validate that the bar chart and representing that data. Then click on the "Show data for next year" button.
        which should add the data for the year 2019.
    */
    @Test(dataProvider = "validCredentials")
    public void testCanvasChart(String username, String password) {

        AppPage appPage = new AppPage(driver);
        eyes.open(driver, "Demo App", "canvasChart", new RectangleSize(800, 600));
        appPage.loginAction(username, password);

        appPage.clickCompareExpenses();
        eyes.checkWindow("compareExpenses Window");

        appPage.clickShowDataNextYear();
        eyes.checkWindow("DataNextYear Window");

        eyes.closeAsync();

    }


}
