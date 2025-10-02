import pages.LoginPage;
import pages.TransferFundsPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.CSVFileManager;
import utilities.ConfigReader;

public class TransferFundsTest extends Base {

    @DataProvider(name = "transferData")
    public Object[][] getTransferData() {
        String path = "src/test/resources/funddata.csv";
        CSVFileManager csv = new CSVFileManager(path);
        return csv.getDataAsObjectArray();
    }

    @Test(dataProvider = "transferData")
    public void testTransfer(String amount, String fromAccount, String toAccount) {
        ConfigReader config = new ConfigReader();
        String username = config.getProperty("username");
        String password = config.getProperty("password");

        // Login
        LoginPage login = new LoginPage(driver);
        login.login(username, password);

        // Transfer
        TransferFundsPage transferFundsPage = new TransferFundsPage(driver);
        transferFundsPage.enterAmount(amount);
        transferFundsPage.selectFromAccount(fromAccount);
        transferFundsPage.selectToAccount(toAccount);
        transferFundsPage.clickTransfer();


        if (amount == null || amount.trim().isEmpty()) {
            if (transferFundsPage.isTransferSuccess()) {
                Assert.fail("BUG: Website accepted an empty transfer amount!");
            } else {
                Assert.assertTrue(transferFundsPage.isErrorDisplayed(),
                        "Error should be displayed for empty transfer amount.");
            }
        } else if (amount.startsWith("-")) {   // Negative Amount Case
            if (transferFundsPage.isTransferSuccess()) {
                Assert.fail("BUG: Website accepted a negative transfer amount: " + amount);
            } else {
                Assert.assertTrue(transferFundsPage.isErrorDisplayed(),
                        "Error should be displayed for negative amount.");
            }
        } else if (fromAccount.equals(toAccount)) {   // Same Account Case
            if (transferFundsPage.isTransferSuccess()) {
                Assert.fail("BUG: Website accepted transfer between the same account: " + fromAccount);
            } else {
                Assert.assertTrue(transferFundsPage.isErrorDisplayed(),
                        "Error should be displayed when from and to accounts are the same.");
            }
        }  else {   // Valid Case
        Assert.assertTrue(transferFundsPage.isTransferSuccess(),
                "Transfer should be successful for valid data.");


        Assert.assertEquals(transferFundsPage.getConfirmationAmount(), amount,
                "Displayed transfer amount does not match input.");
        Assert.assertEquals(transferFundsPage.getConfirmationFromAccount(), fromAccount,
                "Displayed 'From Account' does not match input.");
        Assert.assertEquals(transferFundsPage.getConfirmationToAccount(), toAccount,
                "Displayed 'To Account' does not match input.");
    }


}
}