package dataObjects.enumData;

public enum DepartStation {
    SAI_GON("Sài Gòn"),
    HANOI("Hà Nội"),
    DANANG("Đà Nẵng"),
    NHA_TRANG("Nha Trang"),
    HUE ("Huế");


    private final String value;

    DepartStation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
