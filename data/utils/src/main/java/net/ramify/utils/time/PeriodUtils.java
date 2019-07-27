package net.ramify.utils.time;

import java.time.Period;

public class PeriodUtils {

    private static final double DAYS_PER_YEAR = 365.2422;
    private static final double DAYS_PER_MONTH = DAYS_PER_YEAR / 12d;

    public static int approximateCompare(final Period p1, final Period p2) {
        return Long.compare(approximateDays(p1), approximateDays(p2));
    }

    public static long approximateDays(final Period p1) {
        return (long) (p1.getYears() * DAYS_PER_YEAR)
                + (long) (p1.getMonths() * DAYS_PER_MONTH)
                + p1.getDays();
    }

}
