import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pages.LoginPage;
import pages.TransferFundsPage;
import utilities.CSVFileManager;
import utilities.ConfigReader;

public class TransferFundsTest extends Base {

    private static final Logger log = Logger.getLogger(TransferFundsTest.class);

    @DataProvider(name = "transferData")
    public Object[][] getTransferData() {
        String path = "src/test/resources/funddata.csv";
        CSVFileManager csv = new CSVFileManager(path);
        return csv.getDataAsObjectArray();
    }

    @Test(dataProvider = "transferData")
    public void testTransfer(String amount, String fromAccount, String toAccount) {
        log.info("Starting transfer test with data - Amount: " + amount + ", From: " + fromAccount + ", To: " + toAccount);

        ConfigReader config = new ConfigReader();
        String username = config.getProperty("username");
        String password = config.getProperty("password");

        log.info("Logging in with user: " + username);
        LoginPage login = new LoginPage(driver);
        login.login(username, password);


        TransferFundsPage transferFundsPage = new TransferFundsPage(driver);
        log.info("Entering transfer details");
        transferFundsPage.enterAmount(amount);
        transferFundsPage.selectFromAccount(fromAccount);
        transferFundsPage.selectToAccount(toAccount);
        transferFundsPage.clickTransfer();


        if (amount == null || amount.trim().isEmpty()) {
            log.warn("Testing with empty amount");
            if (transferFundsPage.isTransferSuccess()) {
                log.error("BUG: Website accepted an empty transfer amount!");
                Assert.fail("BUG: Website accepted an empty transfer amount!");
            } else {
                Assert.assertTrue(transferFundsPage.isErrorDisplayed(), "Expected error for empty transfer amount.");
                log.info("Error displayed as expected for empty amount");
            }

        } else if (amount.startsWith("-")) {
            log.warn("Testing with negative amount: " + amount);
            if (transferFundsPage.isTransferSuccess()) {
                log.error("BUG: Website accepted negative amount: " + amount);
                Assert.fail("BUG: Website accepted negative amount: " + amount);
            } else {
                Assert.assertTrue(transferFundsPage.isErrorDisplayed(), "Expected error for negative amount.");
                log.info("Error displayed as expected for negative amount");
            }

        } else if (fromAccount.equals(toAccount)) {
            log.warn("Testing with same account for from/to: " + fromAccount);
            if (transferFundsPage.isTransferSuccess()) {
                log.error("BUG: Website accepted transfer to same account.");
                Assert.fail("BUG: Website accepted transfer between same account: " + fromAccount);
            } else {
                Assert.assertTrue(transferFundsPage.isErrorDisplayed(), "Expected error for same from/to accounts.");
                log.info("Error displayed as expected for same account");
            }

        } else {
            // Valid transfer
            log.info("Valid transfer data, checking success confirmation");
            Assert.assertTrue(transferFundsPage.isTransferSuccess(), "Expected successful transfer.");

            Assert.assertEquals(transferFundsPage.getConfirmationAmount(), amount, "Amount mismatch");
            Assert.assertEquals(transferFundsPage.getConfirmationFromAccount(), fromAccount, "'From' account mismatch");
            Assert.assertEquals(transferFundsPage.getConfirmationToAccount(), toAccount, "'To' account mismatch");

            log.info("Transfer completed successfully with correct confirmation");
        }

        log.info("Test finished for transfer data: Amount = " + amount);
    }
}
