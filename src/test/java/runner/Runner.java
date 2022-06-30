package runner;

import helpers.Log;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.testng.annotations.*;

public class Runner {

    protected WebDriver driver;
    protected Actions actions;

    @Parameters("browserName")
    @BeforeMethod
    public void setUp(String browserName){
        if(browserName.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if(browserName.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        } else if(browserName.equalsIgnoreCase("opera")){
            WebDriverManager.operadriver().setup();
            driver = new OperaDriver();
        }

    }

//    protected void setUp() {
//        WebDriverManager.chromedriver().setup();
//        driver = new ChromeDriver();
//        actions = new Actions(driver);
//    }

    @AfterMethod
    protected void tearDown() {
        Log.info("Tear down driver");
        driver.quit();
    }

}
