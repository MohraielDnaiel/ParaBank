import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import pages.LoginPage;

public class LoginTest extends Base {

    private static final Logger log = Logger.getLogger(LoginTest.class);
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        loginPage = new LoginPage(driver);
    }

    @Test(dataProvider = "jsonLoginData")
    public void loginWithJsonData(String username, String password, boolean isValid) {
        log.info("Starting login test with: Username = " + username + ", Expected valid = " + isValid);

        loginPage.login(username, password);

        if (isValid) {
            boolean loginSuccess = loginPage.isLoginSuccessful();
            log.info("Login success status: " + loginSuccess);
            Assert.assertTrue(loginSuccess, "Expected login to succeed for: " + username);
            log.info("Test passed: Login succeeded as expected for user: " + username);
        } else {
            boolean loginFailed = loginPage.isLoginFailed();
            log.info("Login failure status: " + loginFailed);
            Assert.assertTrue(loginFailed, "Expected login to fail for: " + username);
            log.info("Test passed: Login failed as expected for user: " + username);
        }
    }
}
