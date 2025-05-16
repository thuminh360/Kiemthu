package pageObjects;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ChangePasswordPage extends GeneralPage {

    private final By txtCurrentPassword = By.id("currentPassword");
    private final By txtNewPassword = By.id("newPassword");
    private final By txtConfirmPassword = By.id("confirmPassword");
    private final By btnChangePassword = By.xpath("//input[@value='Change Password']");
    private final By lblSuccessMessage = By.xpath("//p[@class='message success']");

    public ChangePasswordPage(WebDriver driver) {
        super(driver);
    }

    public ChangePasswordPage changePassword(String currentPassword, String newPassword, String confirmPassword) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        driver.findElement(txtCurrentPassword).sendKeys(currentPassword);
        driver.findElement(txtNewPassword).sendKeys(newPassword);
        driver.findElement(txtConfirmPassword).sendKeys(confirmPassword);
        WebElement changePasswordButton = wait.until(ExpectedConditions.elementToBeClickable(btnChangePassword));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", changePasswordButton);
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
}