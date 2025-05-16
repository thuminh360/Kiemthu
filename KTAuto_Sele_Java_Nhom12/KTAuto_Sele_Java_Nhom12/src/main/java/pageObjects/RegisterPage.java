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

public class RegisterPage extends GeneralPage {
    private final By txtEmail = By.xpath("//input[@id='email']");
    private final By txtPassword = By.xpath("//input[@id='password']");
    private final By txtConfirmPassword = By.xpath("//input[@id='confirmPassword']");
    private final By txtPid = By.xpath("//input[@id='pid']");
    private final By btnRegister = By.xpath("//input[@value='Register']");
    private final By lblSuccessMessage = By.xpath("//*[@id='content']");
    private final By lblErrorMessage = By.xpath("//p[@class='message error']");
    private final By lblPasswordError = By.xpath("//label[@for='password' and @class='validation-error']");
    private final By lblPidError = By.xpath("//label[@for='pid' and @class='validation-error']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }
    public RegisterPage register(User user, String confirmPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        driver.findElement(txtEmail).sendKeys(user.getUsername());
        driver.findElement(txtPassword).sendKeys(user.getPassword());
        driver.findElement(txtConfirmPassword).sendKeys(confirmPassword);
        driver.findElement(txtPid).sendKeys(user.getPid());
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(btnRegister));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", registerButton);
        return this;
    }
    public String getSuccessMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(lblSuccessMessage));
            return successMessage.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }
    public String getErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorMessage)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public String getPasswordErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblPasswordError)).getText();
        } catch (Exception e) {
            return "";
        }
    }

    // Lấy thông báo lỗi cho PID
    public String getPidErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblPidError)).getText();
        } catch (Exception e) {
            return "";
        }
    }
}