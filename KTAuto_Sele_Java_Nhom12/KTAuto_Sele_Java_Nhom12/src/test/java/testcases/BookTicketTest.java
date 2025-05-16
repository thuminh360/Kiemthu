package testcases;

import Common.Constant.Constant;
import dataObjects.Ticket;
import dataObjects.User;
import dataObjects.enumData.ArriveStation;
import dataObjects.enumData.DepartStation;
import dataObjects.enumData.SeatType;
import dataObjects.enumData.TicketAmount;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import pageObjects.*;

public class BookTicketTest {
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        Constant.WEBDRIVER = new ChromeDriver();
        Constant.WEBDRIVER.manage().window().maximize();

        homePage = new HomePage(Constant.WEBDRIVER);
        homePage.open();

        User newUser = new User(Constant.USERNAME, Constant.PASSWORD);
        homePage.gotoLoginPage().login(newUser.getUsername(), newUser.getPassword());
    }

    @Test (description = "User can book 1 ticket at a time")
    public void TC14() {
        Ticket ticket = Constant.TICKET_TC14;

        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        bookTicketPage.bookTicket(
                ticket.getDepartDate(),
                ticket.getDepartFrom(),
                ticket.getArriveAt(),
                ticket.getSeatType(),
                ticket.getTicketAmount()
        );

        Assert.assertEquals(bookTicketPage.getSuccessMessage(),
                Constant.TICKET_BOOKED_SUCCESS_MESSAGE,
                Constant.TICKET_BOOKING_MESSAGE_INCORRECT);
        Assert.assertTrue(bookTicketPage.isBookedTicketInformationCorrect(ticket),
                Constant.TICKET_INFORMATION_MISMATCH);
    }
    @Test (description = "User Can Open Book Ticket Page From Timetable")
    public void TC15() {
        homePage.gotoTimetablePage();
        TimetablePage timetablePage = new TimetablePage(Constant.WEBDRIVER);

        String departFrom = DepartStation.HUE.getValue();
        String arriveAt = ArriveStation.SAI_GON.getValue();
        boolean isBookTicketLinkClicked = timetablePage.clickBookTicketLink(departFrom, arriveAt);

        if (isBookTicketLinkClicked) {
            BookTicketPage bookTicketPage = new BookTicketPage(Constant.WEBDRIVER);
            String displayedDepartStation = bookTicketPage.getSelectedDepartStation();
            String displayedArriveStation = bookTicketPage.getSelectedArriveStation();

            Assert.assertEquals(displayedDepartStation, departFrom, Constant.DEPART_STATION_MISMATCH_MESSAGE);
            Assert.assertEquals(displayedArriveStation, arriveAt, Constant.ARRIVE_STATION_MISMATCH_MESSAGE);
        } else {
            String message = String.format(Constant.NO_BOOK_TICKET_LINK_MESSAGE, departFrom, arriveAt);
            System.out.println(message);
            Assert.fail(message);
        }
    }
    @Test (description = "User Can Cancel Ticket")
    public void TC16() {
        BookTicketPage bookTicketPage = homePage.gotoBookTicketPage();
        bookTicketPage.bookTicketWithDefaultValues();
        String ticketId = bookTicketPage.getTicketIdFromUrl();

        homePage.open();
        MyTicketPage myTicketPage = homePage.gotoMyTicketPage();
        myTicketPage.cancelTicket(ticketId);
        Assert.assertFalse(myTicketPage.isTicketPresent(ticketId), Constant.TICKET_NOT_CANCELLED_MESSAGE);
    }

    @AfterMethod
    public void tearDown() {
        if (Constant.WEBDRIVER != null) {
            Constant.WEBDRIVER.quit();
        }
    }
}