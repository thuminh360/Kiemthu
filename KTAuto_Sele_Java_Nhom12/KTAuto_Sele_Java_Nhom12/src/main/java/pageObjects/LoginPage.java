package pageObjects;

import Common.Constant.Constant;
import dataObjects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends GeneralPage {

    private final By _txtUsername = By.xpath("//input[@id='username']");
    private final By _txtPassword = By.xpath("//input[@id='password']");
    private final By _btnLogin = By.xpath("//input[@value='login']");
    private final By _lblLoginErrorMsg = By.xpath("//p[@class='message error LoginForm']");
    private final By lnkForgotPassword = By.xpath("//a[@href='/Account/ForgotPassword.cshtml']");
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    public LoginPage login(String username, String password) {
        driver.findElement(_txtUsername).clear();
        driver.findElement(_txtUsername).sendKeys(username);
        driver.findElement(_txtPassword).clear();
        driver.findElement(_txtPassword).sendKeys(password);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(_btnLogin));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginButton);
        loginButton.click();

        return this;
    }
    public LoginPage login(User user) {
        return login(user.getUsername(), user.getPassword());
    }
    public LoginPage attemptLoginWithWrongPassword(User user, String wrongPassword, int attempts) {
        for (int attempt = 1; attempt <= attempts; attempt++) {
            try {
                if (!isLoginPageDisplayed()) {
                    driver.get(Constant.RAILWAY_URL);
                    gotoLoginPage();
                    if (!isLoginPageDisplayed()) {
                        throw new IllegalStateException();
                    }
                }
                login(user.getUsername(), wrongPassword);
            } catch (Exception ignored) {}
        }
        return this;
    }
    public ForgotPasswordPage gotoForgotPasswordPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        WebElement forgotPasswordLink = wait.until(ExpectedConditions.elementToBeClickable(lnkForgotPassword));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", forgotPasswordLink);
        return new ForgotPasswordPage(driver);
    }
    public String getLoginErrorMessage() {
        return getErrorMessage();
    }
    public boolean isLoginPageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(_txtUsername)).isDisplayed() &&
                wait.until(ExpectedConditions.visibilityOfElementLocated(_txtPassword)).isDisplayed() &&
                wait.until(ExpectedConditions.visibilityOfElementLocated(_btnLogin)).isDisplayed();
    }
}