package Common.Constant;
import Common.Common.Utilities;
import dataObjects.Ticket;
import dataObjects.enumData.*;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static WebDriver WEBDRIVER;
    public static final List<String> MESSAGES = new ArrayList<>();
    public static void logMessage(String message) {
        MESSAGES.add(message);
    }

    // Thông tin trang web và đăng nhập
    public static final String RAILWAY_URL = "http://railwayb1.somee.com/Page/HomePage.cshtml";
    public static final String USERNAME = "tt123@gmail.com";
    public static final String PASSWORD = "updatedpass123";
    public static final String INVALID_USERNAME = "TEGGDGFFG@gmail.com";
    public static final String INVALID_PASSWORD = "wrongpassword";
    public static final String UPDATED_PASSWORD = "updatedpass123";
    public static final String NEW_EMAIL = "qa" + String.valueOf(System.currentTimeMillis()).substring(5) + "@mailinator.com";
    public static final String NEW_PASSWORD = "newpassword123";
    public static final String NEW_PID = "123456789";
    // Thời gian chờ (seconds)
    public static final int LONG_WAIT_TIME = 60;
    public static final int WAIT_TIME = 60;
    public static final int ALERT_WAIT_TIME = 10;
    public static final int ALERT_CHECK_INTERVAL = 500;
    public static final int REFRESH_WAIT_TIME = 5000;


    public static final String RESET_PASSWORD_URL = "http://railwayb1.somee.com/Account/PasswordReset?resetToken=dummy-token";

    // Thông tin đặt vé cho TC14
    public static final Ticket TICKET_TC14 = new Ticket(
            null,
            Utilities.getDepartDateInFuture(5),
            DepartStation.SAI_GON,
            ArriveStation.NHA_TRANG,
            SeatType.SOFT_BED_AC,
            TicketAmount.ONE
    );
    // Thông tin đặt vé cho TC16
    public static final Ticket TICKET_TC16 = new Ticket(
            null,
            Utilities.getDepartDateInFuture(5),
            DepartStation.HUE,
            ArriveStation.NHA_TRANG,
            SeatType.SOFT_BED_AC,
            TicketAmount.ONE
    );
    // Thông báo cho test case 1
    public static final String WELCOME_MESSAGE_TEMPLATE = "Welcome %s"; // Thêm \n để khớp với thực tế
    public static final String WELCOME_MESSAGE_ERROR = "Welcome message does not match expected!";
    // Thông báo cho test case 2
    public static final String LOGIN_FORM_ERROR_MESSAGE = "There was a problem with your login and/or errors exist in your form.";
    public static final String LOGIN_BLANK_USERNAME_ERROR = "Error message does not match when attempting to login with blank username";
    // Thông báo cho test case 3
    public static final String LOGIN_INVALID_PASSWORD_ERROR = "Error message does not match when attempting to login with invalid password";
    // Thông báo lỗi cho TC05
    public static final String LOGIN_ATTEMPTS_EXCEEDED_MESSAGE = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
    public static final String LOGIN_ATTEMPTS_EXCEEDED_ERROR = "Error message does not match after 4 failed login attempts";
    // Thông báo lỗi cho TC06
    public static final String CHANGE_PASSWORDTAB_NOT_DISPLAYED_ERROR_MESSAGE = "Change Password tab is not displayed!";
    public static final String MY_TICKETTAB_NOT_DISPLAYED_ERROR_MESSAGE = "My Ticket tab is not displayed!";
    public static final String LOG_OUT_TAB_NOT_DISPLAYED_ERROR_MESSAGE = "Log out tab is not displayed!";

    public static final String MY_TICKET_REDIRECT_ERROR_MESSAGE = "Did not redirect to My ticket page!";
    public static final String CHANGE_PASSWORD_REDIRECT_ERROR_MESSAGE = "Did not redirect to Change password page!";
    // Thông báo lỗi cho TC07
    public static final String REGISTRATION_SUCCESS_MESSAGE = "Thank you for registering your account";
    public static final String REGISTRATION_ERROR_MESSAGE = "Registration success message does not match!";
    // Thông báo lỗi cho TC08
    public static final String LOGIN_NOT_ACTIVATED_ERROR_MESSAGE = "Invalid username or password. Please try again.";
    public static final String LOGIN_ERROR_MESSAGE = "Login error message does not match!";
    // Thông báo lỗi cho TC09

    public static final String CHANGE_PASSWORD_SUCCESS_MESSAGE = "Your password has been updated";
    public static final String CHANGE_PASSWORD_ERROR_MESSAGE = "Change Password success message does not match";
    // Thông báo lỗi cho TC09
    public static final String REGISTRATION_CONFIRM_PASSWORD_ERROR="message does not match";
    public static final String REGISTRATION_CONFIRM_PASSWORD_MISMATCH_MESSAGE = "There're errors in the form. Please correct the errors and try again.";

    // Thông báo lỗi cho TC11
    public static final String INVALID_PASSWORD_LENGTH_MESSAGE = "Invalid password length.";
    public static final String INVALID_PID_LENGTH_MESSAGE = "Invalid ID length.";

    // Thông báo lỗi cho TC12
    public static final String RESET_TOKEN_ERROR_MESSAGE = "The password reset token is incorrect or may be expired. Visit the forgot password page to generate a new one.";
    public static final String INVALID_RESET_TOKEN_MESSAGE = "The password reset token is invalid.";

    // Thông báo lỗi cho TC13
    public static final String RESET_PASSWORD_MISMATCH_ERROR_MESSAGE = "Could not reset password. Please correct the errors and try again.";
    public static final String CONFIRM_PASSWORD_MISMATCH_MESSAGE = "The password confirmation did not match the new password.";

    // Thông báo TC14
    public static final String TICKET_BOOKING_MESSAGE_INCORRECT = "Thông báo đặt vé không đúng!";
    public static final String TICKET_INFORMATION_MISMATCH = "Thông tin vé hiển thị không khớp với thông tin đã chọn!";
    public static final String TICKET_BOOKED_SUCCESS_MESSAGE = "Ticket booked successfully!";
    // Thông báo TC15
    public static final String DEPART_STATION_MISMATCH_MESSAGE = "The 'Depart from' value does not match the expected value!";
    public static final String ARRIVE_STATION_MISMATCH_MESSAGE = "The 'Arrive at' value does not match the expected value!";
    public static final String NO_BOOK_TICKET_LINK_MESSAGE = "No 'book ticket' link found for the route from %s to %s. Please check if the schedule exists or if the link is available.";
    // Thông báo TC16
    public static final String TICKET_NOT_CANCELLED_MESSAGE = "Ticket could not be cancelled!";
    public static final String ALERT_HANDLING_ERROR = "Could not handle confirmation popup!";
    public static final String CANCEL_BUTTON_NOT_FOUND = "Cancel button not found for ticket ID: %s";
    public static final String TICKET_NOT_CANCELLED_WITH_ID = "Ticket with ID %s is still present after cancellation!";

    public static final String TIMETABLE_COLUMN_NOT_FOUND = "Required columns (Depart Station, Arrive Station, Book Ticket) not found in Timetable header!";
    public static final String TIMETABLE_ERROR_MESSAGE = "Error message displayed on Timetable page: %s";
}