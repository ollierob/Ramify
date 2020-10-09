package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class AfterDate<D extends ChronoLocalDate> extends AbstractDateRange {

    public static AfterDate<?> strictlyAfter(final DateRange range) {
        final var latest = range.latestInclusive().orElse(null);
        if (latest != null) return strictlyAfter(latest);
        throw new UnsupportedOperationException();
    }

    public static AfterDate<?> strictlyAfter(final ExactDate date) {
        return new AfterDate<>(date.date());
    }

    public static <D extends ChronoLocalDate> AfterDate<D> strictlyAfter(final D date) {
        return new AfterDate<>(date);
    }

    private final D start;

    AfterDate(final D start) {
        this.start = start;
    }

    @Nonnull
    @Override
    public Optional<D> earliestInclusive() {
        return Optional.of(start);
    }

    @Nonnull
    @Override
    public Optional<D> latestInclusive() {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "After[" + start + "]";
    }

}
