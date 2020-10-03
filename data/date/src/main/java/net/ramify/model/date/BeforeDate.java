package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class BeforeDate<D extends ChronoLocalDate> extends AbstractDateRange {

    public static DateRange strictlyBefore(final DateRange range) {
        final var latest = range.latestInclusive();
        if (latest.isPresent()) return strictlyBefore(latest.get());
        throw new UnsupportedOperationException(); //TODO
    }

    public static <D extends ChronoLocalDate> BeforeDate<D> strictlyBefore(final D date) {
        return new BeforeDate<>(date, false);
    }

    public static BeforeDate<?> strictlyBefore(final int year) {
        return new BeforeDate<>(LocalDate.of(year, 1, 1), false);
    }

    public static BeforeDate<?> approximatelyBefore(final int year) {
        return new BeforeDate<>(LocalDate.of(year, 1, 1), true);
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

    @Nonnull
    @Override
    public Optional<? extends DateRange> intersection(final DateRange that) {
        if (that instanceof BeforeDate) return this.intersection((BeforeDate<?>) that);
        return super.intersection(that);
    }

    public Optional<BeforeDate<?>> intersection(final BeforeDate<?> that) {
        return that.date.isAfter(this.date)
                ? Optional.of(this)
                : Optional.of(that);
    }

    @Override
    public String toString() {
        return "Before[" + date + "]";
    }

}
