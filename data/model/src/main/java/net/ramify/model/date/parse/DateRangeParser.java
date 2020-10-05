package net.ramify.model.date.parse;

import net.ramify.model.date.DateRange;
import net.ramify.model.util.provider.Parser;

import java.time.Month;

public interface DateRangeParser extends Parser<DateRange> {

    default Month readMonth(final String month) {
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
