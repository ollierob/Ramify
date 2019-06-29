package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

class ApproximateDate implements DateRange {

    private final ChronoLocalDate date;
    private final Period delta;

    ApproximateDate(final ChronoLocalDate date, final Period delta) {
        this.date = date;
        this.delta = delta;
    }

    @Nonnull
    @Override
    public Optional<ChronoLocalDate> earliestInclusive() {
        return Optional.of(date.minus(delta));
    }

    @Nonnull
    @Override
    public Optional<ChronoLocalDate> latestInclusive() {
        return Optional.of(date.plus(delta));
    }

    @Override
    public boolean isApproximate() {
        return true;
    }

}
