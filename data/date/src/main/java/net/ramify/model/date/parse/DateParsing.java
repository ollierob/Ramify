package net.ramify.model.date.parse;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class DateParsing {

    public static ChronoLocalDate parseEnglishDate(final String date) {
        if (date.length() == 4) return englishYear(Integer.parseInt(date));
        return LocalDate.parse(date);
    }

    public static ChronoLocalDate englishYear(final int year) {
        return LocalDate.of(year, 1, 1);
    }

}
