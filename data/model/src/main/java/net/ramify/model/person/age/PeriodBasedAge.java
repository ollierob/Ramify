package net.ramify.model.person.age;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.time.Period;
import java.util.Optional;

class PeriodBasedAge implements Age {

    private final Period period;

    PeriodBasedAge(final Period period) {
        Preconditions.checkArgument(!period.isNegative(), "Cannot have a negative age: %s", period);
        this.period = period;
    }

    @Nonnull
    @Override
    public Period lowerBound() {
        return period;
    }

    @Nonnull
    @Override
    public Period upperBound() {
        return period;
    }

    @Nonnull
    @Override
    public Optional<Period> exact() {
        return Optional.of(period);
    }

}
