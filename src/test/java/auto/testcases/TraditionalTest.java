package auto.testcases;

import auto.pages.AppPage;
import auto.utility.Init;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static auto.pages.AppPage.*;
import static org.testng.Assert.assertTrue;


public class TraditionalTest extends Init {

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



    /*
       This test is to validate the Login Page UI Elements
    */
    @Test
    public void testLoginPage() {

        AppPage appPage = new AppPage(driver);
        appPage.verifyLoginPageTitle();
        appPage.verifyLoginPageHeader();
        appPage.verifyUsernamelabel();
        appPage.verifyPasswordlabel();
        appPage.verifyLoginlabel();

    }


    /*
        Data-Driven Test
        If you don’t enter the username and password and click the login button, it should throw an error
    */
    @Test(dataProvider = "noUsernamePassword")
    public void testLoginNoUsernamePassword(String username, String password) {

        AppPage appPage = new AppPage(driver);

        appPage.verifyLoginPageTitle();
        appPage.loginAction(username, password);

        String actualMsg = appPage.getMsgFailure();
        assertTrue(actualMsg.contains(MSG_BOTH_USERNAME_PASSWORD),
                "Actual '" + actualMsg + "' should be same as expected '" + MSG_BOTH_USERNAME_PASSWORD + "'.");
    }


    /*
        Data-Driven Test
        If you only enter the username and click the login button, it should throw an error
   */
    @Test(dataProvider = "onlyUsername")
    public void testLoginOnlyUsername(String username, String password) {

        AppPage appPage = new AppPage(driver);
        appPage.verifyLoginPageTitle();
        appPage.loginAction(username, password);

        String actualMsg = appPage.getMsgFailure();
        assertTrue(actualMsg.contains(MSG_ONLY_USERNAME),
                "Actual '" + actualMsg + "' should be same as expected '" + MSG_ONLY_USERNAME + "'.");
    }


    /*
        Data-Driven Test
        If you only enter the password and click the login button, it should throw an error
    */
    @Test(dataProvider = "onlyPassword")
    public void testLoginOnlyPassword(String username, String password) {

        AppPage appPage = new AppPage(driver);
        appPage.verifyLoginPageTitle();
        appPage.loginAction(username, password);

        String actualMsg = appPage.getMsgFailure();
        assertTrue(actualMsg.contains(MSG_ONLY_PASSWORD),
                "Actual '" + actualMsg + "' should be same as expected '" + MSG_ONLY_PASSWORD + "'.");
    }

    /*
         Data-Driven Test
         If you enter both username (any value) and password (any value), it should log you in.
    */
    @Test(dataProvider = "validCredentials")
    public void testValidLogin(String username, String password) {

        AppPage appPage = new AppPage(driver);

        appPage.verifyLoginPageTitle();
        appPage.loginAction(username, password);

        String actualMsg = appPage.getMsgSuccess();
        assertTrue(actualMsg.contains(MSG_SUCCESS),
                "Actual '" + actualMsg + "' should be same as expected '" + MSG_SUCCESS + "'.");
    }

    /*
         Table Sort Test
         This test should click on the "Amounts" header, and verify that the column is in ascending order
         and that each row’s data stayed in tact after the sorting.
    */
    @Test(dataProvider = "validCredentials")
    public void testTableSort(String username, String password) throws Exception{

        AppPage appPage = new AppPage(driver);

        appPage.verifyLoginPageTitle();
        appPage.loginAction(username, password);

        String actualMsg = appPage.getMsgSuccess();
        assertTrue(actualMsg.contains(MSG_SUCCESS),
                "Actual '" + actualMsg + "' should be same as expected '" + MSG_SUCCESS + "'.");

        appPage.validateSortAmount();

    }

    /*
    Canvas Chart Test
    This is an empty test as it not possible using selenium webdriver because canvas chart is displayed as an image in the webpage
     */
    @Test(dataProvider = "validCredentials")
    public void testCanvasChart(String username, String password) {

    }

    /*
    Dynamic Content Test
    This is an empty test as it not possible using selenium webdriver because ads are dynamic images
     */
    @Test(dataProvider = "validCredentials")
    public void testDynamicContent(String username, String password) {

    }

}
