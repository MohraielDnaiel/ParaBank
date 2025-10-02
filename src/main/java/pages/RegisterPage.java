package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage {
    private WebDriver driver;

    // Locators
    private By registerLink= By.xpath("//a[contains(text(), 'Register')]");
    private By firstName = By.id("customer.firstName");
    private By lastName = By.id("customer.lastName");
    private By address = By.id("customer.address.street");
    private By city = By.id("customer.address.city");
    private By state = By.id("customer.address.state");
    private By zipCode = By.id("customer.address.zipCode");
    private By phone = By.id("customer.phoneNumber");
    private By ssn = By.id("customer.ssn");
    private By username = By.id("customer.username");
    private By password = By.id("customer.password");
    private By confirmPassword = By.id("repeatedPassword");
    private By registerBtn = By.xpath("//input[@value='Register']");
    private By successMsg = By.xpath("//p[contains(., 'successfully')]");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillRegistrationForm(String fName, String lName, String addr,
                                     String c, String st, String zip, String ph,
                                     String ssnVal, String uname, String pwd, String confirmPwd) {
        driver.findElement(registerLink).click();
        driver.findElement(firstName).sendKeys(fName);
        driver.findElement(lastName).sendKeys(lName);
        driver.findElement(address).sendKeys(addr);
        driver.findElement(city).sendKeys(c);
        driver.findElement(state).sendKeys(st);
        driver.findElement(zipCode).sendKeys(zip);
        driver.findElement(phone).sendKeys(ph);
        driver.findElement(ssn).sendKeys(ssnVal);
        driver.findElement(username).sendKeys(uname);
        driver.findElement(password).sendKeys(pwd);
        driver.findElement(confirmPassword).sendKeys(confirmPwd);
    }

    public void clickRegister() {
        driver.findElement(registerBtn).click();
    }

    public boolean isRegistrationSuccessful() {
        return driver.findElement(successMsg).isDisplayed();
    }
}
