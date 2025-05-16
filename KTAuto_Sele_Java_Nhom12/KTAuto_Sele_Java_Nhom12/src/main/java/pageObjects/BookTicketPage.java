package pageObjects;

import Common.Constant.Constant;
import dataObjects.Ticket;
import dataObjects.enumData.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BookTicketPage {
    private final WebDriver driver;

    // Locators
    private static final By DDL_DEPART_DATE = By.xpath("//select[@name='Date']");
    private static final By DDL_DEPART_STATION = By.xpath("//select[@name='DepartStation']");
    private static final By DDL_ARRIVE_STATION = By.xpath("//select[@name='ArriveStation']");
    private static final By DDL_SEAT_TYPE = By.xpath("//select[@name='SeatType']");
    private static final By DDL_TICKET_AMOUNT = By.xpath("//select[@name='TicketAmount']");
    private static final By BTN_BOOK_TICKET = By.xpath("//input[@type='submit' and @value='Book ticket']");
    private static final By LBL_SUCCESS_MSG = By.xpath("//div[@id='content']/h1");
    private static final By TICKET_INFO_TABLE = By.xpath("//table[@class='MyTable WideTable']");
    private static final By HEADER_ROW = By.xpath("//table[@class='MyTable WideTable']//tr[@class='TableSmallHeader']");
    private static final String DATA_ROW_XPATH = "//table[@class='MyTable WideTable']//tr[@class='OddRow']";

    public BookTicketPage(WebDriver driver) {
        this.driver = driver;
    }

    private void selectFromDropdown(By locator, String value) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(locator));
        new Select(dropdown).selectByVisibleText(value);
    }

    public void bookTicket(String date, DepartStation from, ArriveStation to, SeatType seatType, TicketAmount amount) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        selectFromDropdown(DDL_DEPART_DATE, date);
        selectFromDropdown(DDL_DEPART_STATION, from.getValue());
        selectFromDropdown(DDL_ARRIVE_STATION, to.getValue());
        selectFromDropdown(DDL_SEAT_TYPE, seatType.getValue());
        selectFromDropdown(DDL_TICKET_AMOUNT, String.valueOf(amount.getValue()));

        WebElement bookButton = wait.until(ExpectedConditions.elementToBeClickable(BTN_BOOK_TICKET));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookButton);
    }
    public void bookTicketWithDefaultValues() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        WebElement bookButton = wait.until(ExpectedConditions.elementToBeClickable(BTN_BOOK_TICKET));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", bookButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", bookButton);
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LBL_SUCCESS_MSG)).getText();
    }

    public String getSelectedDepartStation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        return new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(DDL_DEPART_STATION)))
                .getFirstSelectedOption().getText();
    }

    public String getSelectedArriveStation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        return new Select(wait.until(ExpectedConditions.visibilityOfElementLocated(DDL_ARRIVE_STATION)))
                .getFirstSelectedOption().getText();
    }

    private String getTableValue(String columnName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        WebElement headerRow = wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER_ROW));
        List<WebElement> headers = headerRow.findElements(By.tagName("th"));
        int columnIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().toLowerCase().contains(columnName.toLowerCase())) {
                columnIndex = i + 1;
                break;
            }
        }
        if (columnIndex == -1) {
            throw new RuntimeException("Không tìm thấy cột '" + columnName + "' trong bảng!");
        }
        By locator = By.xpath(DATA_ROW_XPATH + "/td[" + columnIndex + "]");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    public String getDepart() {
        return getTableValue("depart station");
    }

    public String getArrive() {
        return getTableValue("arrive station");
    }

    public boolean isBookedTicketInformationCorrect(Ticket ticket) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(TICKET_INFO_TABLE));
        } catch (Exception e) {
            return false;
        }
        return getTableValue("depart date").equals(ticket.getDepartDate()) &&
                getTableValue("depart station").equals(ticket.getDepartFrom().getValue()) &&
                getTableValue("arrive station").equals(ticket.getArriveAt().getValue()) &&
                getTableValue("seat type").equals(ticket.getSeatType().getValue()) &&
                getTableValue("amount").equals(String.valueOf(ticket.getTicketAmount().getValue()));
    }


    public String getTicketIdFromUrl() {
        new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME))
                .until(ExpectedConditions.urlContains("SuccessPage.cshtml"));
        String[] urlParts = driver.getCurrentUrl().split("id=");
        if (urlParts.length <= 1) {
            throw new RuntimeException("Không tìm thấy ticket ID trong URL!");
        }
        return urlParts[1].split("&")[0];
    }
}