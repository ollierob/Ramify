package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Collection;
import java.util.Optional;

public class ClosedDateRange extends AbstractDateRange {

    public static DateRange of(final DateRange s, final DateRange e) {
        final var earliest = s.earliestInclusive().orElse(null);
        if (earliest == null) return s;
        final var latest = e.latestInclusive().orElse(null);
        if (latest == null) return e;
        return ClosedDateRange.of(earliest, latest);
    }

    public static DateRange of(final Collection<? extends HasDate> dates) {
        switch (dates.size()) {
            case 0:
                return DateRange.NEVER;
            case 1:
                return dates.iterator().next().date();
            default:
                ChronoLocalDate earliest = null;
                ChronoLocalDate latest = null;
                for (final HasDate d : dates) {
                    final var date = d.date();
                    earliest = min(earliest, date.earliestInclusive().orElse(null));
                    latest = max(latest, date.latestInclusive().orElse(null));
                }
                return new ClosedDateRange(earliest, latest);
        }
    }

    private static ChronoLocalDate max(final ChronoLocalDate d1, final ChronoLocalDate d2) {
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return d1.isAfter(d2) ? d1 : d2;
    }

    private static ChronoLocalDate min(final ChronoLocalDate d1, final ChronoLocalDate d2) {
        if (d1 == null) return d2;
        if (d2 == null) return d1;
        return d1.isBefore(d2) ? d1 : d2;
    }

    public static DateRange of(final ChronoLocalDate earliest, final ChronoLocalDate latest) {
        if (earliest == null && latest == null) return DateRange.ALWAYS;
        if (earliest == null) return BeforeDate.strictlyBefore(latest);
        if (latest == null) return new AfterDate(earliest);
        if (earliest.isAfter(latest)) return DateRange.NEVER;
        if (earliest.compareTo(latest) == 0) return ExactDate.on(earliest);
        return new ClosedDateRange(earliest, latest);
    }

    private final ChronoLocalDate earliest, latest;

    ClosedDateRange(final ChronoLocalDate earliest, final ChronoLocalDate latest) {
        this.earliest = earliest;
        this.latest = latest;
    }

    @Nonnull
    @Override
    public Optional<ChronoLocalDate> earliestInclusive() {
        return Optional.of(earliest);
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return Optional.of(latest);
    }

    @Override
    public String toString() {
        return "Range[" + earliest + "," + latest + "]";
    }

}
