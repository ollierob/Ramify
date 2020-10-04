package net.ramify.model.date;

import java.time.Month;
import java.util.Optional;
import java.util.regex.Pattern;

import static net.ramify.utils.StringUtils.isBlank;

public class DateRangeParser {

    private static final Pattern YEAR = Pattern.compile("\\d{4}");
    private static final Pattern DAY_LONGMONTH_YEAR = Pattern.compile("(\\d{1,2}) ([a-zA-Z]{3,9}) (\\d{4})");

    public static Optional<DateRange> parse(final String date) {
        if (isBlank(date)) return Optional.empty();
        var matcher = YEAR.matcher(date);
        if (matcher.matches()) return Optional.of(new InYears(Integer.parseInt(date)));
        matcher = DAY_LONGMONTH_YEAR.matcher(date);
        if (matcher.matches()) return Optional.of(ExactDate.on(Integer.parseInt(matcher.group(3)), readMonth(matcher.group(2)), Integer.parseInt(matcher.group(1))));
        return Optional.empty();
    }

    private static Month readMonth(final String month) {
        final var first = month.substring(0, 3).toUpperCase();
        return switch (first) {
            case "JAN" -> Month.JANUARY;
            case "FEB" -> Month.FEBRUARY;
            case "MAR" -> Month.MARCH;
            case "APR" -> Month.APRIL;
            case "MAY" -> Month.MAY;
            case "JUN" -> Month.JUNE;
            case "JUL" -> Month.JULY;
            default -> throw new UnsupportedOperationException("Invalid month: " + month);
        };
    }

}
