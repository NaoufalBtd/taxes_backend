package taxes.service.utils;

import java.time.LocalDate;
import java.time.Year;
import java.util.Date;

public class DateGenerator {
    public static Date[] generateDateRnage(int year, int trimestre) {
        // Calculate the start date of the trimestre
        LocalDate startDate = Year.of(year)
                .atMonth((trimestre - 1) * 3 + 1)
                .atDay(1);

        // Calculate the end date of the trimestre
        LocalDate endDate = startDate.plusMonths(3).minusDays(1);

        // Convert to Date objects
        Date[] dates = new Date[2];
        dates[0] = java.sql.Date.valueOf(startDate);
        dates[1] = java.sql.Date.valueOf(endDate);

        return dates;
    }
}
