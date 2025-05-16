package dataObjects.enumData;

public enum TicketAmount {
    ONE(1);

    private final int value;

    TicketAmount(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
