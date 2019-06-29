package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class ClosedDateRange implements DateRange {

    private final ChronoLocalDate earliest, latest;

    public ClosedDateRange(final ChronoLocalDate earliest, final ChronoLocalDate latest) {
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

}
