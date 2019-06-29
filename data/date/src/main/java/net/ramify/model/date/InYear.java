package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Year;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class InYear implements DateRange {

    private final Year year;

    public InYear(final int isoYear) {
        this(Year.of(isoYear));
    }

    public InYear(final Year year) {
        this.year = year;
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return Optional.of(LocalDate.of(year.getValue(), 1, 1));
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return Optional.of(LocalDate.of(year.getValue(), 12, 31));
    }
}
