package pageObjects;

import Common.Constant.Constant;
import Common.Common.PageUrls;
import dataObjects.enumData.MainMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class GeneralPage {
    protected final WebDriver driver;
    private final String dynamicMenuItem = "//div[@id='menu']//a[span[(text())='%s']]";
    private final By lblWelcomeMessage = By.xpath("//div[@class='account']/strong");
    private final By lblErrorMessage = By.xpath("//p[@class='message error LoginForm']");

    public GeneralPage(WebDriver driver) {
        this.driver = driver;
    }
    protected By getDynamicMenuItem(MainMenu menu) {
        return By.xpath(String.format(dynamicMenuItem, menu.getValue()));
    }

    public LoginPage gotoLoginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        wait.until(ExpectedConditions.elementToBeClickable(getDynamicMenuItem(MainMenu.LOGIN))).click();
        return new LoginPage(driver);
    }

    public GeneralPage gotoHomePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        WebElement homeTab = wait.until(ExpectedConditions.elementToBeClickable(getDynamicMenuItem(MainMenu.HOME)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", homeTab);
        wait.until(ExpectedConditions.urlContains("HomePage.cshtml"));
        return this;
    }

    public MyTicketPage gotoMyTicketPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        wait.until(ExpectedConditions.elementToBeClickable(getDynamicMenuItem(MainMenu.MY_TICKET))).click();
        return new MyTicketPage(driver);
    }
    public ChangePasswordPage gotoChangePasswordPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(getDynamicMenuItem(MainMenu.CHANGE_PASSWORD))).click();
        return new ChangePasswordPage(driver);
    }
    public RegisterPage gotoRegisterPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        wait.until(ExpectedConditions.elementToBeClickable(getDynamicMenuItem(MainMenu.REGISTER))).click();
        return new RegisterPage(driver);
    }

    public boolean isMenuTabDisplayed(MainMenu menu) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            wait.until(ExpectedConditions.urlContains("HomePage.cshtml"));
            wait.until(ExpectedConditions.presenceOfElementLocated(getDynamicMenuItem(menu)));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(getDynamicMenuItem(menu))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isLoggedIn() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            wait.until(ExpectedConditions.urlContains("HomePage.cshtml"));
            String welcomeMessage = getWelcomeMessage();
            if (welcomeMessage == null || welcomeMessage.isEmpty()) {
                Constant.logMessage("Đăng nhập không thành công: Không tìm thấy thông báo chào mừng.");
                return false;
            }
            return true;
        } catch (Exception e) {
            Constant.logMessage("Lỗi khi kiểm tra trạng thái đăng nhập: " + e.getMessage());
            return false;
        }
    }

    public String getWelcomeMessage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
            wait.until(ExpectedConditions.urlContains("HomePage.cshtml"));
            wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
            WebElement welcomeElement = wait.until(ExpectedConditions.presenceOfElementLocated(lblWelcomeMessage));
            String message = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent;", welcomeElement);
            return message.trim().replaceAll("\\s+", " ").replaceAll("(?i)<br\\s*/?>", "\n").trim();
        } catch (Exception e) {
            return "";
        }
    }
    public TimetablePage gotoTimetablePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.LONG_WAIT_TIME));
        wait.until(ExpectedConditions.elementToBeClickable(getDynamicMenuItem(MainMenu.TIME_TABLE))).click();
        return new TimetablePage(driver);
    }
    public BookTicketPage gotoBookTicketPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.LONG_WAIT_TIME));
        wait.until(ExpectedConditions.elementToBeClickable(getDynamicMenuItem(MainMenu.BOOK_TICKET))).click();

        return new BookTicketPage(driver);
    }

    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(lblErrorMessage));
        return driver.findElement(lblErrorMessage).getText();
    }
}