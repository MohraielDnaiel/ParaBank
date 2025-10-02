package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BillPayPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By billPayLink = By.xpath("//a[text()='Bill Pay']");
    private By payeeNameInput = By.name("payee.name");
    private By addressInput = By.name("payee.address.street");
    private By cityInput = By.name("payee.address.city");
    private By stateInput = By.name("payee.address.state");
    private By zipInput = By.name("payee.address.zipCode");
    private By phoneInput = By.name("payee.phoneNumber");

    private By accountInput = By.name("payee.accountNumber");
    private By verifyAccountInput = By.name("verifyAccount");

    private By amountInput = By.name("amount");
    private By fromAccountDropdown = By.name("fromAccountId");

    private By sendPaymentBtn = By.xpath("//input[@value='Send Payment']");

    private By confirmationMessage = By.xpath("//h1[contains(text(),'Bill Payment Complete')]");
    private By confirmationAmount = By.id("amount");
    private By confirmationPayee = By.id("payeeName");

    public BillPayPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToBillPay() {
        wait.until(ExpectedConditions.elementToBeClickable(billPayLink)).click();
    }

    public void enterPayeeDetails(String name, String address, String city, String state,
                                  String zip, String phone, String account, String verifyAccount) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(payeeNameInput)).sendKeys(name);
        driver.findElement(addressInput).sendKeys(address);
        driver.findElement(cityInput).sendKeys(city);
        driver.findElement(stateInput).sendKeys(state);
        driver.findElement(zipInput).sendKeys(zip);
        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(accountInput).sendKeys(account);
        driver.findElement(verifyAccountInput).sendKeys(verifyAccount);
    }

    public void enterPaymentAmount(String amount) {
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(amountInput));
        amountField.clear();
        amountField.sendKeys(amount);
    }

    public void selectFromAccount(String accountNumber) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(fromAccountDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(accountNumber);
    }

    public void clickSendPayment() {
        wait.until(ExpectedConditions.elementToBeClickable(sendPaymentBtn)).click();
    }

    public boolean isPaymentSuccess() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage)).isDisplayed();
    }

    public String getConfirmationAmount() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationAmount)).getText().trim();
        return text.replace("$", "").replace(".00", "").trim();
    }

    public String getConfirmationPayee() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationPayee)).getText().trim();
    }
}
