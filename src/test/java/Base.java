import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import utilities.ConfigReader;
import utilities.ExcelFileManager;
import utilities.JsonFileManager;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class Base {
    protected WebDriver driver;
    protected ConfigReader config;

    @BeforeMethod
    public void OpenWebsite() {
        driver = new FirefoxDriver();
        config = new ConfigReader();
        driver.get("https://parabank.parasoft.com/parabank/register.htm");




        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
    }
    @DataProvider(name = "jsonLoginData")
    public Object[][] loginDataProvider() throws Exception {
        JsonFileManager json = new JsonFileManager("src/main/java/utilities/data.json");
        List<Map<String, String>> records = json.getDataList();

        Object[][] data = new Object[records.size()][3];

        for (int i = 0; i < records.size(); i++) {
            Map<String, String> record = records.get(i);
            data[i][0] = record.get("username");
            data[i][1] = record.get("password");
            data[i][2] = Boolean.parseBoolean(record.get("valid"));
        }

        return data;
    }
    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() {
        return new Object[][]{
                {"mohra", "Doe", "123 Street", "Cairo", "Cairo", "12345", "0123456789", "123456", "Mohra", "Password", "Password"},
                {"Sara", "Ali", "456 Avenue", "Giza", "Giza", "54321", "0112345678", "654321", "Danial", "Pass@123", "Pass@123"}
        };
    }

    @DataProvider(name = "credentials")
    public Object[][] credentialsDataProvider() {
        ExcelFileManager excel = new ExcelFileManager("src/test/resources/excel.xlsx", "Sheet1");
        return excel.getSheetDataAsArray();
    }

    @AfterMethod
    public void failureTestCases(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String testName = result.getName();
            takeScreenshot(testName + "_failed.png");
        }
        driver.quit();
    }

    public void takeScreenshot(String fileName) {
        try {

            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            File dest = new File(System.getProperty("user.dir") + File.separator + fileName);
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved to: " + dest.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error while saving screenshot: " + e.getMessage());
        } catch (WebDriverException e) {
            System.err.println("WebDriver is already closed, cannot take screenshot.");
        }
    }
}