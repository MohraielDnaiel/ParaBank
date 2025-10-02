import org.testng.Assert;
import org.testng.annotations.*;

import pages.LoginPage;

public class LoginTest extends Base {

    private LoginPage loginPage;


    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
    }



    @Test(dataProvider = "jsonLoginData")
    public void loginWithJsonData(String username, String password, boolean isValid) {
        loginPage.login(username, password);

        if (isValid) {
            Assert.assertTrue(loginPage.isLoginSuccessful(), "Expected login to succeed for: " + username);
        } else {
            Assert.assertTrue(loginPage.isLoginFailed(), "Expected login to fail for: " + username);
        }
    }
}
