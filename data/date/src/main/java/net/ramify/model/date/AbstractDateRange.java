package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.util.Optional;

abstract class AbstractDateRange implements DateRange {

    @Nonnull
    @Override
    public Optional<? extends DateRange> intersection(final DateRange that) {
        return Optional.ofNullable(DateRanges.intersection(this, that));
    }

    public DateRange approximately() {
        return this.isApproximate()
                ? this
                : new ApproximateDateRange(this);
    }

    public boolean equals(final Object that) {
        return that instanceof DateRange
                && (this == that || this.equals((DateRange) that));
    }

    protected boolean equals(final DateRange that) {
        return DateRanges.equals(this, that);
    }

    @Override
    public int hashCode() {
        return DateRanges.hash(this);
    }

}
