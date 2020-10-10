package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.util.Objects;
import java.util.Optional;

public class ExactDate implements DateRange {

    public static ExactDate on(final int year, final Month month, final int dayOfMonth) {
        return on(LocalDate.of(year, month, dayOfMonth));
    }

    public static ExactDate on(final ChronoLocalDate date) {
        return new ExactDate(date);
    }

    private final ChronoLocalDate date;

    ExactDate(@Nonnull final ChronoLocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    @Nonnull
    public ChronoLocalDate date() {
        return date;
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> exact() {
        return Optional.of(date);
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return Optional.of(date);
    }

    @Nonnull
    @Override
    public LocalDate earliestIsoDate() {
        return LocalDate.from(date);
    }

    @Nonnull
    @Override
    public LocalDate latestIsoDate() {
        return LocalDate.from(date);
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return Optional.of(date);
    }

    @Override
    public boolean isExact() {
        return true;
    }

    @Override
    public Optional<DateRange> intersection(final DateRange that) {
        return this.intersects(that) ? Optional.of(this) : Optional.empty();
    }

    @Override
    public boolean intersects(final DateRange that) {
        return that.contains(date);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var exactDate = (ExactDate) o;
        return date.equals(exactDate.date);
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

    @Override
    public String toString() {
        return date.toString();
    }

}
