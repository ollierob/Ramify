package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class AfterDate extends AbstractDateRange {

    private final ChronoLocalDate start;

    public AfterDate(final ChronoLocalDate start) {
        this.start = start;
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return Optional.of(start);
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return Optional.empty();
    }

}
