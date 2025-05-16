package testcases;

import Common.Constant.Constant;
import dataObjects.User;
import dataObjects.enumData.MainMenu;
import Common.Common.PageUrls;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObjects.*;

public class LoginTest {

    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Pre-condition");
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();
        homePage = new HomePage(Constant.WEBDRIVER);
        homePage.open();

    }

    @Test(description = "TC01 - User can log into Railway with valid username and password")
    public void TC01() {
        User user = new User(Constant.USERNAME, Constant.PASSWORD);
        loginPage = homePage.gotoLoginPage();
        loginPage.login(user);
        String expectedWelcomeMessage = String.format(Constant.WELCOME_MESSAGE_TEMPLATE, Constant.USERNAME);
        Assert.assertEquals(homePage.getWelcomeMessage(), expectedWelcomeMessage, Constant.WELCOME_MESSAGE_ERROR);
    }
    @Test(description = "TC02 - User cannot login with blank Username textbox")
    public void TC02() {
        final User userWithBlankUsername = new User("", Constant.PASSWORD);
        loginPage = homePage.gotoLoginPage();
        loginPage.login(userWithBlankUsername);
        String actualErrorMessage = loginPage.getLoginErrorMessage();
        String expectedErrorMessage = Constant.LOGIN_FORM_ERROR_MESSAGE;
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage,Constant.LOGIN_BLANK_USERNAME_ERROR
        );
    }
    @Test(description = "TC03 - User cannot login with invalid password")
    public void TC03() {
        final User userWithInvalidPassword = new User(Constant.USERNAME, Constant.INVALID_PASSWORD);
        loginPage = homePage.gotoLoginPage();
        loginPage.login(userWithInvalidPassword);
        String actualErrorMessage = loginPage.getLoginErrorMessage();
        String expectedErrorMessage = Constant.LOGIN_FORM_ERROR_MESSAGE;
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, Constant.LOGIN_INVALID_PASSWORD_ERROR);
    }
    @Test(description = "TC04 - Redirect to Login page when un-logged user clicks Book ticket tab")
    public void TC04() {
        homePage.gotoBookTicketPage();
        loginPage = new LoginPage(Constant.WEBDRIVER);
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }
    @Test(description = "TC05 - System shows message when user enters wrong password several times")
    public void TC05() {
        final User validUser = new User(Constant.USERNAME, Constant.PASSWORD);
        final String wrongPassword = Constant.INVALID_PASSWORD;
        final int maxAttempts = 4;
        loginPage = homePage.gotoLoginPage();
        loginPage.attemptLoginWithWrongPassword(validUser, wrongPassword, maxAttempts);

        Assert.assertEquals(loginPage.getLoginErrorMessage(),
                Constant.LOGIN_ATTEMPTS_EXCEEDED_MESSAGE,
                Constant.LOGIN_ATTEMPTS_EXCEEDED_ERROR);
    }
    @Test(description = "TC06 - Additional pages display once user logged in")
    public void TC06() {
        loginPage = homePage.gotoLoginPage()
                .login(new User(Constant.USERNAME, Constant.PASSWORD));

        Assert.assertTrue(homePage.isLoggedIn());
        Assert.assertTrue(homePage.isMenuTabDisplayed(MainMenu.MY_TICKET), Constant.MY_TICKETTAB_NOT_DISPLAYED_ERROR_MESSAGE);
        Assert.assertTrue(homePage.isMenuTabDisplayed(MainMenu.CHANGE_PASSWORD), Constant.CHANGE_PASSWORD_REDIRECT_ERROR_MESSAGE);
        Assert.assertTrue(homePage.isMenuTabDisplayed(MainMenu.LOGOUT), Constant.LOG_OUT_TAB_NOT_DISPLAYED_ERROR_MESSAGE);
        homePage.gotoMyTicketPage();
        Assert.assertEquals(Constant.WEBDRIVER.getCurrentUrl(), PageUrls.getUrl(MainMenu.MY_TICKET),
                Constant.MY_TICKET_REDIRECT_ERROR_MESSAGE);
        homePage.gotoChangePasswordPage();
        Assert.assertEquals(Constant.WEBDRIVER.getCurrentUrl(), PageUrls.getUrl(MainMenu.CHANGE_PASSWORD),
                Constant.CHANGE_PASSWORD_REDIRECT_ERROR_MESSAGE);
    }
    @Test(description = "TC07 - User can create new account")
    public void TC07() {
        RegisterPage registerPage = homePage.gotoRegisterPage();
        User newUser = new User(Constant.NEW_EMAIL, Constant.NEW_PASSWORD, Constant.NEW_PID);
        registerPage.register(newUser, Constant.NEW_PASSWORD);
        Assert.assertEquals(registerPage.getSuccessMessage(), Constant.REGISTRATION_SUCCESS_MESSAGE, Constant.REGISTRATION_ERROR_MESSAGE);
    }
    @Test(description = "TC08 - User can't login with an account hasn't been activated")
    public void TC08() {
        User unactivatedUser = new User(Constant.INVALID_USERNAME, Constant.INVALID_PASSWORD);
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(unactivatedUser);
        Assert.assertEquals(loginPage.getErrorMessage(), Constant.LOGIN_NOT_ACTIVATED_ERROR_MESSAGE,Constant.LOGIN_ERROR_MESSAGE);
    }

    @Test(description = "TC09 - User can change password")
    public void TC09() {
        RegisterPage registerPage = homePage.gotoRegisterPage();
        User newUser = new User(Constant.NEW_EMAIL, Constant.NEW_PASSWORD, Constant.NEW_PID);
        registerPage.register(newUser, Constant.NEW_PASSWORD);

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(newUser);
        ChangePasswordPage changePasswordPage = homePage.gotoChangePasswordPage();

        changePasswordPage.changePassword(Constant.NEW_PASSWORD, Constant.UPDATED_PASSWORD, Constant.UPDATED_PASSWORD);
        Assert.assertEquals(changePasswordPage.getSuccessMessage(), Constant.CHANGE_PASSWORD_SUCCESS_MESSAGE,Constant.CHANGE_PASSWORD_ERROR_MESSAGE);
    }
    @Test(description = "TC10 - User can't create account with Confirm password not matching Password")
    public void TC10() {
        RegisterPage registerPage = homePage.gotoRegisterPage();
        User newUser = new User(Constant.NEW_EMAIL, Constant.NEW_PASSWORD, Constant.NEW_PID);
        registerPage.register(newUser, Constant.INVALID_PASSWORD);
        Assert.assertEquals(registerPage.getErrorMessage(), Constant.REGISTRATION_CONFIRM_PASSWORD_MISMATCH_MESSAGE,Constant.REGISTRATION_CONFIRM_PASSWORD_ERROR);
    }
    @Test(description = "TC11 - User can't create account while password and PID fields are empty")
    public void TC11()  {
        RegisterPage registerPage = homePage.gotoRegisterPage();
        User newUser = new User(Constant.NEW_EMAIL, "", "");
        registerPage.register(newUser, "");
        Assert.assertEquals(registerPage.getErrorMessage(), Constant.REGISTRATION_CONFIRM_PASSWORD_MISMATCH_MESSAGE);
        Assert.assertEquals(registerPage.getPasswordErrorMessage(), Constant.INVALID_PASSWORD_LENGTH_MESSAGE);
        Assert.assertEquals(registerPage.getPidErrorMessage(), Constant.INVALID_PID_LENGTH_MESSAGE);
    }



    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");

        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
        }
    }
}