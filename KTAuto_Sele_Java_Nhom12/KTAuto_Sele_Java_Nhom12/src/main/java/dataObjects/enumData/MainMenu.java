package dataObjects.enumData;

public enum MainMenu {
    HOME("Home"),
    LOGIN("Login"),
    REGISTER("Register"),
    FAQ("FAQ"),
    CONTACT("Contact"),
    TICKET_PRICE("Ticket price"),
    TIME_TABLE("Timetable"),
    BOOK_TICKET("Book ticket"),
    MY_TICKET("My ticket"),
    CHANGE_PASSWORD("Change password"),
    LOGOUT("Log out");

    private String value;

    MainMenu(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}