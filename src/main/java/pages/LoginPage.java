package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    WebDriver driver;

    // Locators
    private By usernameField = By.xpath("//input[@type='text' and @name='username']");
    private By passwordField = By.xpath("//input[@type='password' and @name='password']");
    private By loginButton = By.xpath("//input[@class='button' and @type='submit']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Actions
    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
    public boolean isLoginSuccessful() {

        return !driver.findElements(By.xpath("//h1[contains(text(), 'Accounts Overview')]")).isEmpty();
    }

    public boolean isLoginFailed() {

        return !driver.findElements(By.xpath("//p[@class='error' and contains(text(), 'username and password could not be verified')]")).isEmpty();
    }

}
