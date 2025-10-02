package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TransferFundsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By linkTransfer = By.xpath("//a[text()='Transfer Funds']");
    private By amountInput = By.id("amount");
    private By fromAccountDropdown = By.id("fromAccountId");
    private By toAccountDropdown = By.id("toAccountId");
    private By transferButton = By.xpath("//input[@value='Transfer']");
    private By confirmationMessage = By.xpath("//h1[contains(text(),'Transfer Complete')]");
    private By errorMessage = By.xpath("//*[contains(@class,'error')]");
    private By confirmationAmount = By.id("amountResult");
    private By confirmationFromAccount = By.id("fromAccountIdResult");
    private By confirmationToAccount = By.id("toAccountIdResult");

    // Constructor
    public TransferFundsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions
    public void enterAmount(String amount) {
        driver.findElement(linkTransfer).click();
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(amountInput));
        amountField.clear();
        amountField.sendKeys(amount);
    }

    public void selectFromAccount(String accountNumber) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(fromAccountDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(accountNumber);  // أو selectByValue(accountNumber)
    }

    public void selectToAccount(String accountNumber) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(toAccountDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(accountNumber);
    }

    public void clickTransfer() {
        wait.until(ExpectedConditions.elementToBeClickable(transferButton)).click();
    }

    // Validations
    public boolean isTransferSuccess() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(confirmationMessage)).size() > 0;
    }

    public boolean isErrorDisplayed() {
        return driver.findElements(errorMessage).size() > 0;
    }

    public String getConfirmationAmount() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationAmount));
        String text = el.getText().trim();

        if (text.isEmpty()) {
            text = el.getAttribute("value").trim();
        }
        text = text.replace("$", "").replace(",", "").trim();
        if (text.endsWith(".00")) {
            text = text.substring(0, text.length() - 3);
        }
        return text;
    }

    public String getConfirmationFromAccount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationFromAccount)).getText();
    }

    public String getConfirmationToAccount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationToAccount)).getText();
    }
}
