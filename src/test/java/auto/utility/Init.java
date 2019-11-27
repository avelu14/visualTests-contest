package auto.utility;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.log4j.Logger.getLogger;


public class Init extends DriverFactory {

    //  private static final String URL = "https://demo.applitools.com/hackathon.html";
        private static final String URL = "https://demo.applitools.com/hackathonV2.html";

    protected WebDriver driver = null;
    private static final long IMPLICIT_TIME = 5;
    private static Logger logger = getLogger(Init.class.getName());


    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("# Setup.");
        driver = getDriver();
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_TIME, SECONDS);
        driver.manage().window().maximize();
    }


    @AfterMethod(alwaysRun = true)
    public void teardown() {
        logger.info("# Teardown.");
        if (driver != null)
            driver.quit();
    }
}
