package pageObjects;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForgotPasswordPage extends GeneralPage {

    private final By txtEmail = By.xpath("//input[@id='email']");
    private final By btnSendInstructions = By.xpath("//input[@value='Send Instructions']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    public ForgotPasswordPage sendInstructions(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        driver.findElement(txtEmail).sendKeys(email);
        WebElement sendButton = wait.until(ExpectedConditions.elementToBeClickable(btnSendInstructions));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendButton);
        return this;
    }
}