package pageObjects;

import Common.Constant.Constant;
import dataObjects.enumData.MainMenu;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends GeneralPage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public HomePage open() {
        driver.get(Constant.RAILWAY_URL);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Constant.WAIT_TIME));
        wait.until(driver -> ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        return this;
    }


}