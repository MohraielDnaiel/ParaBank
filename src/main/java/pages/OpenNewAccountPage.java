package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class OpenNewAccountPage {
    WebDriver driver;

    private By openAccountLink = By.linkText("Open New Account");
    private By accountTypeDropdown = By.id("type");
    private By fromAccountDropdown = By.id("fromAccountId");
    private By openNewAccountButton = By.cssSelector("input[value='Open New Account']");
    private By accountNumberText = By.id("newAccountId");

    public OpenNewAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToOpenNewAccountPage() {
        driver.findElement(openAccountLink).click();
    }

    public void selectAccountType(String accountType) {
        Select select = new Select(driver.findElement(accountTypeDropdown));
        select.selectByVisibleText(accountType);
    }

    public void selectFromAccount(String accountNumber) {
        Select select = new Select(driver.findElement(fromAccountDropdown));
        select.selectByVisibleText(accountNumber);
    }

    public void submit() {
        driver.findElement(openNewAccountButton).click();
    }

    public String getNewAccountNumber() {
        return driver.findElement(accountNumberText).getText();
    }


    public List<String> getAvailableFromAccounts() {
        Select select = new Select(driver.findElement(fromAccountDropdown));
        List<WebElement> options = select.getOptions();
        List<String> accounts = new ArrayList<>();
        for (WebElement option : options) {
            accounts.add(option.getText().trim());
        }
        return accounts;
    }
}
