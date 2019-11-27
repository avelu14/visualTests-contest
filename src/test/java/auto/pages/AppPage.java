package auto.pages;

import auto.utility.Services;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import java.util.*;
import java.util.stream.Collectors;
import static org.testng.Assert.assertEquals;



public class AppPage extends Services {

    private final static String HEADING = "Login Form";
    private final static String USER_LABEL = "Username";
    private final static String PASSWORD_LABEL = "Password";
    private final static String LOGIN_LABEL = "Log In";
    final static String TITLE = "ACME demo app";
    public static final String MSG_SUCCESS = "Compare Expenses";
    public static final String MSG_ONLY_USERNAME = "Password must be present";
    public static final String MSG_ONLY_PASSWORD = "Username must be present";
    public static final String MSG_BOTH_USERNAME_PASSWORD = "Both Username and Password must be present";



    //XPATH
    private String xpathHeading = "/html/body/div/div/h4";
    private String xpathUsername = "//*[@id=\"username\"]";
    private String xpathPassword = "//*[@id=\"password\"]";
    private String xpathLoginBtn = "//*[@id=\"log-in\"]";
    private String xpathUsernameLabel = "/html/body/div/div/form/div[1]/label";
    private String xpathPasswordLabel = "/html/body/div/div/form/div[2]/label";
    private String xpathErrorMessage = "//*[@class=\"alert alert-warning\"]";
    private String xpathMsg = "//*[@id=\"showExpensesChart\"]";
    private String xpathAmountBtn = "//*[@id=\"amount\"]";
    private String xpathTransactionTbl = "//*[@id=\"transactionsTable\"]/tbody";
    private String xpathCompareExpenses = "//*[@id=\"showExpensesChart\"]";
    private String xpathShowData =  "//*[@id=\"addDataset\"]";


    public AppPage(WebDriver driver) {
        super(driver);
    }

    public void verifyLoginPageTitle() {
        String actualTitle = driver.getTitle();
        assertEquals(actualTitle, TITLE,
                "Actual title " + actualTitle + " should be same as expected " + TITLE);
    }

    public void verifyLoginPageHeader() {
        waitForElement(xpathHeading);
        WebElement headerEle = driver.findElement(By.xpath(xpathHeading));
        String actualHeading = headerEle.getText();
        assertEquals(actualHeading, HEADING,
                "Actual heading '" + actualHeading + "' should be same as expected '" + HEADING + "'.");
    }

    public void verifyUsernamelabel() {
        waitForElement(xpathUsernameLabel);
        WebElement userEle = driver.findElement(By.xpath(xpathUsernameLabel));
        String actualUsername = userEle.getText();
        assertEquals(actualUsername, USER_LABEL,
                "Actual heading '" + actualUsername + "' should be same as expected '" + USER_LABEL + "'.");
    }

    public void verifyPasswordlabel() {
        waitForElement(xpathPasswordLabel);
        WebElement passEle = driver.findElement(By.xpath(xpathPasswordLabel));
        String actualPassword = passEle.getText();
        assertEquals(actualPassword, PASSWORD_LABEL,
                "Actual heading '" + actualPassword + "' should be same as expected '" + PASSWORD_LABEL + "'.");
    }

    public void verifyLoginlabel() {
        waitForElement(xpathLoginBtn);
        WebElement loginEle = driver.findElement(By.xpath(xpathLoginBtn));
        String actualLogin = loginEle.getText();
        assertEquals(actualLogin, LOGIN_LABEL,
                "Actual heading '" + actualLogin + "' should be same as expected '" + LOGIN_LABEL + "'.");
    }


    public String getMsgSuccess() {
        waitForElementVisible(xpathMsg);
        return driver.findElement(By.xpath(xpathMsg)).getText().trim();
    }

    public String getMsgFailure() {
        waitForElementVisible(xpathErrorMessage);
        return driver.findElement(By.xpath(xpathErrorMessage)).getText().trim();
    }

    public void loginAction(String username, String password) {
        type(xpathUsername, username);
        type(xpathPassword, password);
        click(xpathLoginBtn);
    }

    public void validateSortAmount() throws Exception{

        LinkedHashMap<Double, String> actualValues;
        LinkedHashMap<Double, String> sortedValues;
        LinkedHashMap<Double, String> sortedActual = new LinkedHashMap<>();

        WebElement actualtable = driver.findElement(By.xpath(xpathTransactionTbl));
        actualValues = captureWebTableRows(actualtable);

        actualValues.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> sortedActual.put(x.getKey(), x.getValue()));
        click(xpathAmountBtn);

        WebElement sortedtable = driver.findElement(By.xpath(xpathTransactionTbl));
        sortedValues = captureWebTableRows(sortedtable);

        List ActualKeys = sortedActual.keySet().stream().collect(Collectors.toCollection(ArrayList::new));
        List ExpectedKeys = sortedValues.keySet().stream().collect(Collectors.toCollection(ArrayList::new));
        Boolean flag = sortedActual.equals(sortedValues);
           for (int i=0;i<ActualKeys.size();i++){
               if(!(ActualKeys.get(i).equals(ExpectedKeys.get(i))) || !flag)
                     Assert.fail("Values are not equal after sorting table based on amount");

          }

    }

    public void clickOnAmount() {
        click(xpathAmountBtn);
    }

    public void clickCompareExpenses() {
        click(xpathCompareExpenses);
    }

    public void clickShowDataNextYear() {
        click(xpathShowData);
    }


    public LinkedHashMap<Double,String> captureWebTableRows(WebElement table) {

       LinkedHashMap<Double,String> values = new LinkedHashMap<>();

        List<Double> amount = new ArrayList<>();
       List < WebElement > rows_table = table.findElements(By.tagName("tr"));

        String tmp;
        int rows_count = rows_table.size();
        for (int row = 0; row < rows_count; row++) {
            List < WebElement > Columns_row = rows_table.get(row).findElements(By.tagName("td"));

            tmp = (Columns_row.get(Columns_row.size()-1).getText().trim().replace(" USD", "").replace(",",""));
            if(tmp.contains("+"))
                tmp = tmp.replace("+ ","");
            else if(tmp.contains("-"))
                tmp = tmp.replace("- ","-");
           values.put(Double.parseDouble(tmp), rows_table.get(row).getText());
            amount.add(Double.parseDouble(tmp));
            }

        return values;
        }



}