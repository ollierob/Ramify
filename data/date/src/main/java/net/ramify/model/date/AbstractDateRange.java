package net.ramify.model.date;

import javax.annotation.Nonnull;
import java.util.Optional;

abstract class AbstractDateRange implements DateRange {

    @Nonnull
    @Override
    public Optional<DateRange> intersection(DateRange that) {
        return Optional.ofNullable(DateRanges.intersection(this, that));
    }

}
