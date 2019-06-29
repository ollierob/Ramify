package net.ramify.model.date;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class DateParsing {

    public static ChronoLocalDate parse(final String date) {
        return LocalDate.parse(date);
    }

}
