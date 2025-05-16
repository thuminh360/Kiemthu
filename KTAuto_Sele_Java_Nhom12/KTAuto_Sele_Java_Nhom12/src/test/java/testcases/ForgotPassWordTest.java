package testcases;

import Common.Constant.Constant;
import dataObjects.User;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.*;

public class ForgotPassWordTest {
    private HomePage homePage;
    private static String registeredEmail;

    @BeforeMethod
    public void beforeMethod() {
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
        homePage = new HomePage(Constant.WEBDRIVER);
        homePage.open();


    }

    @Test(description = "TC12 - Errors display when password reset token is blank")
    public void TC12() {
        ForgotPasswordPage forgotPage = homePage.gotoLoginPage().gotoForgotPasswordPage();
        forgotPage.sendInstructions(Constant.USERNAME);

        Constant.WEBDRIVER.get(Constant.RESET_PASSWORD_URL);

        PasswordChangeFormPage page = new PasswordChangeFormPage(Constant.WEBDRIVER);
        page.resetPassword(Constant.UPDATED_PASSWORD, Constant.UPDATED_PASSWORD, "");
        Assert.assertEquals(page.getErrorMessage(), Constant.RESET_TOKEN_ERROR_MESSAGE);
        Assert.assertEquals(page.getTokenErrorMessage(), Constant.INVALID_RESET_TOKEN_MESSAGE);
    }

    @Test(description = "TC13 - Errors display if password and confirm password don't match when resetting password")
    public void TC13() {
        ForgotPasswordPage forgotPage = homePage.gotoLoginPage().gotoForgotPasswordPage();
        forgotPage.sendInstructions(Constant.USERNAME);

        Constant.WEBDRIVER.get(Constant.RESET_PASSWORD_URL);

        PasswordChangeFormPage page = new PasswordChangeFormPage(Constant.WEBDRIVER);
        page.resetPassword(Constant.UPDATED_PASSWORD, Constant.INVALID_PASSWORD, "dummy-token");
        Assert.assertEquals(page.getErrorMessage(), Constant.RESET_PASSWORD_MISMATCH_ERROR_MESSAGE);
        Assert.assertEquals(page.getConfirmPasswordErrorMessage(), Constant.CONFIRM_PASSWORD_MISMATCH_MESSAGE);
    }

    @AfterMethod
    public void afterMethod() {
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
        }
    }
}