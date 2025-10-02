
import pages.RegisterPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RegisterTest extends Base {

    @Test(dataProvider = "registrationData")
    public void testUserRegistration(String fName, String lName, String addr, String city,
                                     String state, String zip, String phone, String ssn,
                                     String uname, String pwd, String confirmPwd) {


        RegisterPage registerPage = new RegisterPage(driver);

        // Fill form
        registerPage.fillRegistrationForm(fName, lName, addr, city, state, zip, phone, ssn, uname, pwd, confirmPwd);

        // Submit
        registerPage.clickRegister();

        // Validate
        Assert.assertTrue(registerPage.isRegistrationSuccessful(), "Registration failed!");
    }
}
