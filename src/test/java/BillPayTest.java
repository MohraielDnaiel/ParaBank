
import pages.BillPayPage;
import pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.JsonFileManager;

import java.io.FileNotFoundException;
import java.util.Map;

public class BillPayTest extends Base {
    private BillPayPage billPayPage;
    private LoginPage login;
    private JsonFileManager jsonData;

    @Test
    public void testBillPayment() throws FileNotFoundException {

        login = new LoginPage(driver);
        login.login(config.getProperty("username"), config.getProperty("password"));

        jsonData = new JsonFileManager("src/main/java/utilies/pilldata.json");
        billPayPage = new BillPayPage(driver);

        // 3- Loop over test data
        for (Map<String, String> record : jsonData.getDataList()) {
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


            Assert.assertTrue(billPayPage.isPaymentSuccess(),
                    "Payment failed for payee: " + record.get("name"));

            Assert.assertEquals(billPayPage.getConfirmationAmount(), record.get("amount"),
                    "Amount mismatch for payee: " + record.get("name"));

            Assert.assertEquals(billPayPage.getConfirmationPayee(), record.get("name"),
                    "Payee mismatch for payee: " + record.get("name"));
        }
    }
}
