package Common.Common;

import dataObjects.enumData.MainMenu;

public class PageUrls {
    public static final String getUrl(MainMenu menu) {
        return switch (menu) {
            case HOME -> "http://railwayb1.somee.com/Page/HomePage.cshtml";
            case LOGIN -> "http://railwayb1.somee.com/Account/Login.cshtml";
            case REGISTER -> "http://railwayb1.somee.com/Account/Register.cshtml";
            case FAQ -> "http://railwayb1.somee.com/Page/FAQ.cshtml";
            case CONTACT -> "http://railwayb1.somee.com/Page/Contact.cshtml";
            case TICKET_PRICE -> "http://railwayb1.somee.com/Page/TrainPriceListPage.cshtml";
            case TIME_TABLE -> "http://railwayb1.somee.com/Page/TrainTimeListPage.cshtml";
            case BOOK_TICKET -> "http://railwayb1.somee.com/Page/BookTicketPage.cshtml";
            case MY_TICKET -> "http://railwayb1.somee.com/Page/ManageTicket.cshtml";
            case CHANGE_PASSWORD -> "http://railwayb1.somee.com/Account/ChangePassword.cshtml";
            case LOGOUT -> "http://railwayb1.somee.com/Account/Logout";
            default -> throw new IllegalArgumentException("No URL defined for menu: " + menu);
        };
    }
}