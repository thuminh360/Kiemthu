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

public class TimetablePage {
    private final WebDriver driver;
    private static final By TIMETABLE_TABLE = By.xpath("//table[@class='MyTable WideTable']");
    private static final By HEADER_ROW = By.xpath("//table[@class='MyTable WideTable']//tr[@class='TableSmallHeader']");
    private static final By PAGE_ERROR_MESSAGE = By.xpath("//div[contains(@class, 'error') or contains(@class, 'alert')]");
    private static final String DATA_ROW_XPATH = "//table[@class='MyTable WideTable']/tbody/tr[not(@class='TableSmallHeader')]";
    private static final String BOOK_TICKET_XPATH = ".//td[%d]/a[contains(text(), 'book ticket')]";

    public TimetablePage(WebDriver driver) {
        this.driver = driver;
    }

    private int findColumnIndex(List<WebElement> headers, String columnName) {
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().toLowerCase().contains(columnName)) {
                return i + 1;
            }
        }
        return -1;
    }

    public boolean clickBookTicketLink(String departFrom, String arriveAt) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(Constant.WAIT_TIME));
        wait.until(ExpectedConditions.visibilityOfElementLocated(TIMETABLE_TABLE));
        List<WebElement> errorMessages = Constant.WEBDRIVER.findElements(PAGE_ERROR_MESSAGE);
        if (!errorMessages.isEmpty() && errorMessages.get(0).isDisplayed()) {
            throw new RuntimeException(String.format(Constant.TIMETABLE_ERROR_MESSAGE, errorMessages.get(0).getText()));
        }
        List<WebElement> headers = wait.until(ExpectedConditions.visibilityOfElementLocated(HEADER_ROW))
                .findElements(By.tagName("th"));
        int departCol = findColumnIndex(headers, "depart station");
        int arriveCol = findColumnIndex(headers, "arrive station");
        int bookTicketCol = findColumnIndex(headers, "book ticket");

        if (departCol == -1 || arriveCol == -1 || bookTicketCol == -1) {
            throw new RuntimeException(Constant.TIMETABLE_COLUMN_NOT_FOUND);
        }
        List<WebElement> rows = Constant.WEBDRIVER.findElements(By.xpath(DATA_ROW_XPATH));
        for (WebElement row : rows) {
            String actualDepart = row.findElement(By.xpath(".//td[" + departCol + "]")).getText();
            String actualArrive = row.findElement(By.xpath(".//td[" + arriveCol + "]")).getText();
            if (actualDepart.equals(departFrom) && actualArrive.equals(arriveAt)) {
                String xpath = String.format(BOOK_TICKET_XPATH, bookTicketCol);
                List<WebElement> bookTicketLinks = row.findElements(By.xpath(xpath));
                if (bookTicketLinks.isEmpty()) {
                    return false;
                }
                WebElement link = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[" + bookTicketCol + "]/a[contains(text(), 'book ticket')]")));
                ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", link);
                return true;
            }
        }
        return false;
    }
}