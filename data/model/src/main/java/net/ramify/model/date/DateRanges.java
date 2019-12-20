package net.ramify.model.date;

import javax.annotation.CheckForNull;

import static net.ramify.utils.objects.ComparableUtils.max;
import static net.ramify.utils.objects.ComparableUtils.min;

class DateRanges {

    @CheckForNull
    static DateRange intersection(final DateRange d1, final DateRange d2) {
        final var start = max(d1.earliestIsoDate(), d2.earliestIsoDate());
        final var end = min(d1.latestIsoDate(), d2.latestIsoDate());
        return start.isAfter(end) ? null : ClosedDateRange.of(start, end);
    }

}
