package net.ramify.model.date.parse;

import net.ramify.model.date.AfterDateRange;
import net.ramify.model.date.ApproximateDateRange;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.InYears;
import net.ramify.utils.objects.Functions;

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
            default -> this.parseSpecial(date);
        };
    }

    DateRange parseYear(final String yyyy) {
        try {
            return new InYears(Integer.parseInt(yyyy));
        } catch (final NumberFormatException ex) {
            return null;
        }
    }

    private DateRange parseSpecial(final String date) {
        final var lower = date.toLowerCase();
        if (lower.startsWith("abt")) return Functions.ifNonNull(this.parseWithMatchers(nextDotSpaceOrNumber(date, 3)), ApproximateDateRange::of);
        if (lower.startsWith("aft")) return Functions.ifNonNull(this.parseWithMatchers(nextDotSpaceOrNumber(date, 3)), AfterDateRange::of);
        //TODO bef
        return this.parseWithMatchers(date);
    }

    DateRange parseWithMatchers(final String date) {
        var matcher = DAY_LONGMONTH_YEAR.matcher(date);
        if (matcher.matches()) return ExactDate.on(Integer.parseInt(matcher.group(1)), this.parseMonth(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        return null;
    }

    private static String nextDotSpaceOrNumber(final String s, final int start) {
        int i;
        for (i = start; i < s.length(); i++) {
            final var c = s.charAt(start);
            if (Character.isWhitespace(c) || Character.isDigit(c) || c == '.') break;
        }
        return s.substring(i);
    }

}
