package net.ramify.model.date;

import javax.annotation.CheckForNull;
import java.time.chrono.ChronoLocalDate;
import java.util.Objects;
import java.util.Optional;

import static net.ramify.utils.objects.ComparableUtils.max;
import static net.ramify.utils.objects.ComparableUtils.min;

class DateRanges {

    @CheckForNull
    static DateRange intersection(final DateRange d1, final DateRange d2) {
        final var start = max(d1.earliestIsoDate(), d2.earliestIsoDate());
        final var end = min(d1.latestIsoDate(), d2.latestIsoDate());
        return start.isAfter(end) ? null : ClosedDateRange.of(start, end);
    }

    static boolean equals(final DateRange d1, final DateRange d2) {
        if (d1 == d2) return true;
        if (d1 == null || d2 == null) return d1 == d2;
        return d1.isApproximate() == d2.isApproximate()
                && equals(d1.earliestInclusive(), d2.earliestInclusive())
                && equals(d1.latestInclusive(), d2.latestInclusive());
    }

    static boolean equals(final Optional<? extends ChronoLocalDate> d1, final Optional<? extends ChronoLocalDate> d2) {
        return d1.map(chronoLocalDate -> d2.isPresent() && chronoLocalDate.compareTo(d2.get()) == 0)
                .orElseGet(d2::isEmpty);
    }

    static int hash(final DateRange d) {
        return Objects.hash(d.earliestInclusive(), d.latestInclusive());
    }

}
