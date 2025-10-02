package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountOverviewPage {
    WebDriver driver;

    private By accountsOverviewLink = By.linkText("Accounts Overview");

    public AccountOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToAccountsOverview() {
        driver.findElement(accountsOverviewLink).click();
    }

    public boolean isAccountPresent(String accountNumber) {
        return driver.getPageSource().contains(accountNumber);
    }
}
