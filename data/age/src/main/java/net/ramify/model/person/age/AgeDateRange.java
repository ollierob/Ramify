package net.ramify.model.person.age;

import net.ramify.model.date.DateRange;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

public class AgeDateRange implements DateRange {

    private final Optional<? extends ChronoLocalDate> earliest, latest;

    public AgeDateRange(final Optional<? extends ChronoLocalDate> earliest, final Optional<? extends ChronoLocalDate> latest) {
        this.earliest = earliest;
        this.latest = latest;
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> earliestInclusive() {
        return earliest;
    }

    @Nonnull
    @Override
    public Optional<? extends ChronoLocalDate> latestInclusive() {
        return latest;
    }

    @Nonnull
    @Override
    public Optional<? extends DateRange> intersection(final DateRange that) {
        throw new UnsupportedOperationException(); //TODO
    }

}
