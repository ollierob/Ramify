package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
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
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return Optional.of(date);
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return Optional.of(date);
    }

    public DateRange yearsAgo(final int years) {
        final var period = Period.ofYears(years);
        return new ClosedDateRange(date.minus(period.plusYears(1)), date.minus(period));
    }

}
