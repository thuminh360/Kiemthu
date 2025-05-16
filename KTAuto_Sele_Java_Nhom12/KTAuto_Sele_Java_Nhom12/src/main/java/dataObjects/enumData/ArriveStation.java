package dataObjects.enumData;

public enum ArriveStation {
    SAI_GON("Sài Gòn"),
    HANOI("Hà Nội"),
    DANANG("Đà Nẵng"),
    NHA_TRANG("Nha Trang");

    private final String value;

    ArriveStation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
