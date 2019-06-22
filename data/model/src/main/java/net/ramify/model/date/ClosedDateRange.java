package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

class ClosedDateRange implements DateRange {

    private final ChronoLocalDate earliest, latest;

    ClosedDateRange(ChronoLocalDate earliest, ChronoLocalDate latest) {
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
