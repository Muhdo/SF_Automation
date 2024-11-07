package BaseClasses;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class TestBase {

    public TestBase(){loadPropFile();}

    public Properties testData;
    public static WebDriver driver;



    public void loadPropFile() {
        try {

            testData = new Properties();
            FileInputStream fis = new FileInputStream("src/test/java/Utils/testData.properties");
            testData.load(fis);

        } catch (Exception exp) {
            exp.getMessage();
        }
    }

    @BeforeClass

    public void mainSetup(){
        String browser = testData.getProperty("browser");
        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        else if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        else {
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        // driver.get(testData.getProperty("baseUrl"));
        driver.manage().window().maximize();
        //WebElement element = driver.findElement(By.xpath(""));
        //Actions actions = new Actions(driver);

    }

    public void sleep(int seconds){
        try {
            Thread.sleep(seconds * 1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    @AfterClass
    public void tearDown(){
        if(null != driver){
            driver.quit();
        }

    }
}

