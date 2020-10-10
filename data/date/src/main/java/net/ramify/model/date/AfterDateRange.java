package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class AfterDateRange extends AbstractDateRange {

    public static DateRange of(final DateRange date) {
        final var exact = date.exactValue();
        if (exact.isPresent()) return new AfterDateRange(exact.get());
        final var latest = date.latestInclusive();
        if (latest.isPresent()) return new AfterDateRange(latest.get());
        final var earliest = date.earliestInclusive();
        if (earliest.isPresent()) return new AfterDateRange(earliest.get());
        throw new IllegalArgumentException("Cannot compute after " + date);
    }

    private final ChronoLocalDate earliest;

    public AfterDateRange(final ChronoLocalDate earliest) {
        this.earliest = earliest;
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return Optional.of(earliest.plus(1, ChronoUnit.DAYS));
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return Optional.empty();
    }

}
