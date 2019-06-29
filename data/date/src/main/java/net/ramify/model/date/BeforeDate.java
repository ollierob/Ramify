package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class BeforeDate<D extends ChronoLocalDate> implements DateRange {

    public static DateRange strictlyBefore(final DateRange range) {
        throw new UnsupportedOperationException(); //TODO
    }

    public static <D extends ChronoLocalDate> BeforeDate<D> strictlyBefore(final D date) {
        return new BeforeDate<>(date, false);
    }

    private final D date;
    private final boolean approximate;

    BeforeDate(final D date, final boolean approximate) {
        this.date = date;
        this.approximate = approximate;
    }

    @Nonnull
    @Override
    public Optional<D> earliestInclusive() {
        return Optional.empty();
    }

    @Nonnull
    @Override
    public Optional<D> latestInclusive() {
        return Optional.of(date);
    }

    @Override
    public boolean isApproximate() {
        return approximate;
    }
}
