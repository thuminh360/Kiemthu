package pageObjects;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MyTicketPage {
    private final WebDriver driver;
    private static final By TICKET_TABLE = By.xpath("//table[@class='MyTable']");
    private static final String CANCEL_BUTTON_XPATH_TEMPLATE = "//input[@type='button' and @value='Cancel' and contains(@onclick, 'DeleteTicket(%s)')]";

    public MyTicketPage(WebDriver driver) {
        this.driver = driver;
    }

    private void handleAlerts() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.ALERT_WAIT_TIME));
        try {
            while (true) {
                wait.until(ExpectedConditions.alertIsPresent());
                Constant.WEBDRIVER.switchTo().alert().accept();
                Thread.sleep(Constant.ALERT_CHECK_INTERVAL);
            }
        } catch (Exception e) {
            // No more alerts
        }
    }

    public void cancelTicket(String ticketId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(TICKET_TABLE));

        String xpath = String.format(CANCEL_BUTTON_XPATH_TEMPLATE, ticketId);
        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        if (cancelButton == null) {
            throw new RuntimeException(String.format(Constant.CANCEL_BUTTON_NOT_FOUND, ticketId));
        }

        ((JavascriptExecutor) driver).executeScript("arguments[0].click(); arguments[0].dispatchEvent(new Event('click'));", cancelButton);

        try {
            handleAlerts();
        } catch (Exception e) {
            throw new RuntimeException(Constant.ALERT_HANDLING_ERROR + " " + e.getMessage());
        }

        driver.navigate().refresh();
        wait.until(ExpectedConditions.visibilityOfElementLocated(TICKET_TABLE));
        try {
            Thread.sleep(Constant.REFRESH_WAIT_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (!driver.findElements(By.xpath(xpath)).isEmpty()) {
            throw new RuntimeException(String.format(Constant.TICKET_NOT_CANCELLED_WITH_ID, ticketId));
        }
    }

    public boolean isTicketPresent(String ticketId) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(TICKET_TABLE));

        handleAlerts();

        String xpath = String.format(CANCEL_BUTTON_XPATH_TEMPLATE, ticketId);
        return !driver.findElements(By.xpath(xpath)).isEmpty();
    }
}