import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.BillPayPage;
import pages.LoginPage;
import utilities.JsonFileManager;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.List;

public class BillPayTest extends Base {
    private static final Logger log = Logger.getLogger(BillPayTest.class);

    private BillPayPage billPayPage;
    private LoginPage login;
    private JsonFileManager validData;
    private JsonFileManager invalidData;

    @Test
    public void testBillPaymentWithValidAndInvalidData() throws FileNotFoundException {
        log.info("Starting Bill Payment Test (Valid + Invalid data)");

        // Step 1: Login
        login = new LoginPage(driver);
        log.info("Logging in with username: " + config.getProperty("username"));
        login.login(config.getProperty("username"), config.getProperty("password"));

        // Step 2: Load test data
        log.info("Loading test data...");
        validData = new JsonFileManager("src/main/java/utilities/validPillData.json");
        invalidData = new JsonFileManager("src/main/java/utilities/invalidPillDatajson.json");

        billPayPage = new BillPayPage(driver);

        // Step 3: Run Valid and Invalid tests
        runValidBillPayments(validData.getDataList());
        runInvalidBillPayments(invalidData.getDataList());

        log.info("Finished Bill Payment Test.");
    }

    private void runValidBillPayments(List<Map<String, String>> dataList) {
        log.info("Running Valid Bill Payments...");
        for (Map<String, String> record : dataList) {
            log.info("Testing valid payment for payee: " + record.get("name"));

            billPayPage.navigateToBillPay();

            billPayPage.enterPayeeDetails(
                    record.get("name"),
                    record.get("address"),
                    record.get("city"),
                    record.get("state"),
                    record.get("zip"),
                    record.get("phone"),
                    record.get("account"),
                    record.get("verifyAccount")
            );

            billPayPage.enterPaymentAmount(record.get("amount"));
            billPayPage.selectFromAccount(record.get("fromAccount"));
            billPayPage.clickSendPayment();

            boolean success = billPayPage.isPaymentSuccess();
            log.info("Payment Success Status: " + success);

            Assert.assertTrue(success, "Expected success for: " + record.get("name"));
            Assert.assertEquals(billPayPage.getConfirmationAmount(), record.get("amount"), "Amount mismatch");
            Assert.assertEquals(billPayPage.getConfirmationPayee(), record.get("name"), "Payee mismatch");

            log.info("Valid payment passed for payee: " + record.get("name"));
        }
    }

    private void runInvalidBillPayments(List<Map<String, String>> dataList) {
        log.info("Running Invalid Bill Payments...");
        for (Map<String, String> record : dataList) {
            log.info("Testing invalid payment for payee (expected error): " + record.get("error"));

            billPayPage.navigateToBillPay();

            billPayPage.enterPayeeDetails(
                    record.get("name"),
                    record.get("address"),
                    record.get("city"),
                    record.get("state"),
                    record.get("zip"),
                    record.get("phone"),
                    record.get("account"),
                    record.get("verifyAccount")
            );

            billPayPage.enterPaymentAmount(record.get("amount"));
            billPayPage.selectFromAccount(record.get("fromAccount"));
            billPayPage.clickSendPayment();

            boolean errorDisplayed = billPayPage.isErrorMessageDisplayed();
            log.info("Error Displayed: " + errorDisplayed);

            Assert.assertTrue(errorDisplayed, "Expected failure but payment may have passed for: " + record.get("error"));

            log.info("Correctly failed as expected for: " + record.get("error"));
        }
    }
}
