package net.ramify.model.date;

import javax.annotation.CheckForNull;

class DateRanges {

    @CheckForNull
    static DateRange intersection(final DateRange d1, final DateRange d2) {
        final var s1 = d1.earliestIsoDate();
        final var s2 = d2.earliestIsoDate();
        if (s1.isAfter(s2)) return intersection(d2, d1);
        final var e1 = d1.latestIsoDate();
        if (e1.isBefore(s2)) return null;
        return ClosedDateRange.of(e1, s2);
    }

}
