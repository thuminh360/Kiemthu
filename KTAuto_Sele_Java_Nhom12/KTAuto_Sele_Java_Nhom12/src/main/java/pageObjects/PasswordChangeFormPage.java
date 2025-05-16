package pageObjects;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PasswordChangeFormPage extends GeneralPage {
    private final By txtNewPassword = By.xpath("//*[@id='newPassword']");
    private final By txtConfirmPassword = By.xpath("//*[@id='confirmPassword']");
    private final By txtResetToken = By.xpath("//*[@id='resetToken']");
    private final By btnResetPassword = By.xpath("//input[@value='Reset Password']");
    private final By lblErrorMessage = By.xpath("//p[@class='message error']");
    private final By lblResetTokenError = By.xpath("//label[@for='resetToken' and @class='validation-error']");
    private final By lblConfirmPasswordError = By.xpath("//label[@for='confirmPassword' and @class='validation-error']");

    public PasswordChangeFormPage(WebDriver driver) {
        super(driver);
    }
    public PasswordChangeFormPage resetPassword(String newPassword, String confirmPassword, String resetToken) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        driver.findElement(txtNewPassword).sendKeys(newPassword);
        driver.findElement(txtConfirmPassword).sendKeys(confirmPassword);
        driver.findElement(txtResetToken).sendKeys(resetToken);
        WebElement resetButton = wait.until(ExpectedConditions.elementToBeClickable(btnResetPassword));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", resetButton);
        return this;
    }

    public String getErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorMessage)).getText();
        } catch (Exception e) {
            return "";
        }
    }
    public String getTokenErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblResetTokenError)).getText();
        } catch (Exception e) {
            return "";
        }
    }
    public String getConfirmPasswordErrorMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(lblConfirmPasswordError)).getText();
        } catch (Exception e) {
            return "";
        }
    }
}