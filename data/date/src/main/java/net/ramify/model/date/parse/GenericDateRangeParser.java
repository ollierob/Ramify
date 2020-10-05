package net.ramify.model.date.parse;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.InYears;

import javax.inject.Singleton;
import java.util.regex.Pattern;

@Singleton
public class GenericDateRangeParser implements DateRangeParser {

    private static final Pattern DAY_LONGMONTH_YEAR = Pattern.compile("(\\d{1,2}) ([a-zA-Z]{3,}) \\d{4}");

    @Override
    public DateRange get(final String date) {
        return switch (date.length()) {
            case 0, 1, 2, 3 -> null;
            case 4 -> this.parseYear(date);
            default -> this.parseWithMatchers(date);
        };
    }

    DateRange parseYear(final String date) {
        try {
            return new InYears(Integer.parseInt(date));
        } catch (final NumberFormatException ex) {
            return null;
        }
    }

    DateRange parseWithMatchers(final String date) {
        var matcher = DAY_LONGMONTH_YEAR.matcher(date);
        if (matcher.matches()) return ExactDate.on(Integer.parseInt(matcher.group(1)), this.parseMonth(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        return null;
    }

}
