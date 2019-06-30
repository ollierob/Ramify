package net.ramify.model.date;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Year;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class InYears implements DateRange {

    private final Year start, end;

    public InYears(final int isoYear) {
        this(Year.of(isoYear));
    }

    public InYears(final Year year) {
        this(year, year);
    }

    public InYears(final int start, final int end) {
        this(Year.of(start), Year.of(end));
    }

    public InYears(final Year start, final Year end) {
        Preconditions.checkArgument(!start.isAfter(end));
        this.start = start;
        this.end = end;
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return Optional.of(LocalDate.of(start.getValue(), 1, 1));
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return Optional.of(LocalDate.of(end.getValue(), 12, 31));
    }
}
