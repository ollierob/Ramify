package net.ramify.model.date;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Year;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class InYears extends AbstractDateRange {

    private final Year start, end;
    private final boolean approximate;

    public InYears(final int isoYear) {
        this(isoYear, false);
    }

    public InYears(final int isoYear, final boolean approximate) {
        this(Year.of(isoYear), approximate);
    }

    public InYears(final Year year) {
        this(year, year);
    }

    public InYears(final Year year, final boolean approximate) {
        this(year, year, approximate);
    }

    public InYears(final int start, final int end) {
        this(Year.of(start), Year.of(end));
    }

    public InYears(final Year start, final Year end) {
        this(start, end, false);
    }

    public InYears(final Year start, final Year end, final boolean approximate) {
        Preconditions.checkArgument(!start.isAfter(end));
        this.start = start;
        this.end = end;
        this.approximate = approximate;
    }

    @Override
    public boolean isApproximate() {
        return approximate;
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
