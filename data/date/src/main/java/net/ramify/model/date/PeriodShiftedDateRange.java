package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.Period;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

class PeriodShiftedDateRange extends AbstractDateRange {

    private final DateRange delegate;
    private final Period delta;

    PeriodShiftedDateRange(final DateRange delegate, final Period delta) {
        this.delegate = delegate;
        this.delta = delta;
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return delegate.earliestInclusive().map(d -> d.plus(delta));
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return delegate.latestInclusive().map(d -> d.plus(delta));
    }

}
