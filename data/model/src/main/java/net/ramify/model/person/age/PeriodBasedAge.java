package net.ramify.model.person.age;

import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import java.time.Period;

class PeriodBasedAge implements Age {

    private final Period lower, upper;

    PeriodBasedAge(final Period period) {
        this(period, period);
    }

    PeriodBasedAge(final Period lower, final Period upper) {
        Preconditions.checkArgument(!lower.isNegative(), "Cannot have a negative age: %s", lower);
        this.lower = lower;
        this.upper = upper;
    }

    @Nonnull
    @Override
    public Period lowerBound() {
        return lower;
    }

    @Nonnull
    @Override
    public Period upperBound() {
        return upper;
    }

}
