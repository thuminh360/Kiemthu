package Common.Common;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utilities {
    public static String getDepartDateInFuture(int daysInFuture) {
        LocalDate futureDate = LocalDate.now().plusDays(daysInFuture);
        // Định dạng ngày theo MM/DD/YYYY để khớp với dropdown
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        return futureDate.format(formatter);
    }
}