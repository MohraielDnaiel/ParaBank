import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import pages.AccountOverviewPage;
import pages.LoginPage;
import pages.OpenNewAccountPage;
import utilities.ConfigReader;

import java.util.List;

public class OpenNewAccountTest extends Base {

    private static final Logger log = Logger.getLogger(OpenNewAccountTest.class);

    @Test
    public void testOpenNewCheckingAccount() {
        log.info("Starting test: Open New Checking Account");

        ConfigReader config = new ConfigReader();
        String username = config.getProperty("username");
        String password = config.getProperty("password");

        log.info("Logging in with username: " + username);
        LoginPage login = new LoginPage(driver);
        login.login(username, password);

        OpenNewAccountPage openAccount = new OpenNewAccountPage(driver);
        log.info("Navigating to 'Open New Account' page");
        openAccount.goToOpenNewAccountPage();

        log.info("Selecting account type: CHECKING");
        openAccount.selectAccountType("CHECKING");

        List<String> availableAccounts = openAccount.getAvailableFromAccounts();
        log.info("Available accounts for transfer: " + availableAccounts);

        Assert.assertFalse(availableAccounts.isEmpty(), "No accounts available for transfer");

        String fromAccount = availableAccounts.get(0);
        log.info("Selecting from account: " + fromAccount);
        openAccount.selectFromAccount(fromAccount);

        log.info("Submitting new account request");
        openAccount.submit();

        String newAccountNumber = openAccount.getNewAccountNumber();
        log.info("New account number created: " + newAccountNumber);
        Assert.assertNotNull(newAccountNumber, "New account number is not displayed.");

        AccountOverviewPage overviewPage = new AccountOverviewPage(driver);
        log.info("Navigating to 'Accounts Overview' page");
        overviewPage.navigateToAccountsOverview();

        boolean isPresent = overviewPage.isAccountPresent(newAccountNumber);
        log.info("Is new account visible in overview: " + isPresent);
        Assert.assertTrue(isPresent, "New account does not appear in Accounts Overview.");

        log.info("Test passed: New account created and verified successfully");
    }
}
