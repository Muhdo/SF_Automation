package Utils.ExtentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static BaseClasses.TestBase.driver;

public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter extentSparkReporter;
    public ExtentReports extentReports;
    public ExtentTest test;


    private static String reportPath;
    private static String localDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        LocalDateTime curTime = LocalDateTime.now();
        return dateFormat.format(curTime);
    }

    public void onStart(ITestContext context){
        String currentDate = localDate();
        reportPath = "./Reports/TestReport-" + currentDate + ".html";
        extentSparkReporter = new ExtentSparkReporter(reportPath);
        extentSparkReporter.config().setDocumentTitle("Automation Report");
        extentSparkReporter.config().setReportName("Rick and Morty Challenge");
        extentSparkReporter.config().setTheme(Theme.DARK);

        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        extentReports.setSystemInfo("Tester Name", "Muhammed Oyewumi");
        extentReports.setSystemInfo("Platform", "Google chrome");

    }

    public void onTestSuccess(ITestResult result) {
        test = extentReports.createTest(result.getName());
        test.log(Status.PASS, "Test case passed is: "+ result.getName());
    }

    public void onTestSkipped(ITestResult result) {
        test = extentReports.createTest(result.getName());
        test.log(Status.SKIP, "Test case skipped is: " + result.getName());
    }

    public void onTestFailure(ITestResult result) {
        test = extentReports.createTest(result.getName());
        test.log(Status.FAIL, "Test case failed is: " + result.getName());

        try {
            String currentDate = localDate();
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "./Screenshots/" + result.getName() + "_" + currentDate + ".png";
            FileUtils.copyFile(screenshotFile, new File(screenshotPath));

            test.addScreenCaptureFromPath(screenshotPath, "Screenshot of the failure");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onFinish(ITestContext context) {
        extentReports.flush();
    }
}
