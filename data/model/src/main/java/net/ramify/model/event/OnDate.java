package net.ramify.model.event;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class OnDate implements DateRange {

    private final ChronoLocalDate date;

    public OnDate(final ChronoLocalDate date) {
        this.date = date;
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return Optional.of(date);
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return Optional.of(date);
    }

}
