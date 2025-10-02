import pages.AccountOverviewPage;
import pages.LoginPage;
import pages.OpenNewAccountPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.ConfigReader;

import java.util.List;

public class OpenNewAccountTest extends Base {

    @Test
    public void testOpenNewCheckingAccount() {
        ConfigReader config = new ConfigReader();
        String username = config.getProperty("username");
        String password = config.getProperty("password");

        LoginPage login = new LoginPage(driver);
        login.login(username, password);

        OpenNewAccountPage openAccount = new OpenNewAccountPage(driver);
        openAccount.goToOpenNewAccountPage();
        openAccount.selectAccountType("CHECKING");

        List<String> availableAccounts = openAccount.getAvailableFromAccounts();
        Assert.assertFalse(availableAccounts.isEmpty(), "No accounts available for transfer");

        String fromAccount = availableAccounts.get(0);
        openAccount.selectFromAccount(fromAccount);
        openAccount.submit();

        String newAccountNumber = openAccount.getNewAccountNumber();
        Assert.assertNotNull(newAccountNumber, "New account number is not displayed.");

        AccountOverviewPage overviewPage = new AccountOverviewPage(driver);
        overviewPage.navigateToAccountsOverview();
        Assert.assertTrue(overviewPage.isAccountPresent(newAccountNumber),
                "New account does not appear in Accounts Overview.");
    }

}
