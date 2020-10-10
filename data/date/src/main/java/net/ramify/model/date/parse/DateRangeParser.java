package net.ramify.model.date.parse;

import net.meerkat.objects.Classes;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.util.provider.Parser;

import java.time.Month;
import java.util.Optional;

public interface DateRangeParser extends Parser<DateRange> {

    default Optional<ExactDate> getExact(final String date) {
        return this.maybeGet(date).flatMap(d -> Classes.cast(d, ExactDate.class));
    }

    default Month parseMonth(final String month) {
        final var first = month.substring(0, 3);
        return switch (first) {
            case "jan", "Jan", "JAN" -> Month.JANUARY;
            case "feb", "Feb", "FEB" -> Month.FEBRUARY;
            case "mar", "Mar", "MAR" -> Month.MARCH;
            case "apr", "Apr", "APR" -> Month.APRIL;
            case "may", "May", "MAY" -> Month.MAY;
            case "jun", "Jun", "JUN" -> Month.JUNE;
            case "jul", "July", "JUL" -> Month.JULY;
            case "aug", "Aug", "AUG" -> Month.AUGUST;
            case "sep", "Sep", "SEP" -> Month.SEPTEMBER;
            case "oct", "Oct", "OCT" -> Month.OCTOBER;
            default -> throw new UnsupportedOperationException("Invalid month: " + month);
        };
    }

}
