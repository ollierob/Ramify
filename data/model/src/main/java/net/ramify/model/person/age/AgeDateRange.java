package net.ramify.model.person.age;

import net.ramify.model.date.DateRange;

import javax.annotation.Nonnull;
import java.time.chrono.ChronoLocalDate;
import java.util.Optional;

class AgeDateRange implements DateRange {

    private final Optional<? extends ChronoLocalDate> earliest, latest;

    AgeDateRange(Optional<? extends ChronoLocalDate> earliest, Optional<? extends ChronoLocalDate> latest) {
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
}
