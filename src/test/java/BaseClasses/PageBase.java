package BaseClasses;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class PageBase {

    WebDriver driver;
    WebDriverWait wait;

    public PageBase(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.driver = driver;
    }

    public void waitForVisibility(WebElement element){
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void click(WebElement element){
        waitForVisibility(element);
        element.click();
    }
    public void enterText(WebElement element, String text){
        waitForVisibility(element);
        element.sendKeys(text);
    }

    public void clearInputField(WebElement element){
        element.clear();
    }

    public void ValidateText(WebElement element, String text){
        Assert.assertEquals(element.getText(), text, "Invalid text");
    }



}

