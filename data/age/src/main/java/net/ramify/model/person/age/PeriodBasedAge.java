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
        Preconditions.checkArgument(lower.getYears() >= 0, "Cannot have a negative age: %s", lower);
        Preconditions.checkArgument(upper.getYears() >= 0, "Cannot have a negative age: %s", upper);
        this.lower = lower.normalized();
        this.upper = upper.normalized();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeriodBasedAge that = (PeriodBasedAge) o;
        if (!lower.equals(that.lower)) return false;
        return upper.equals(that.upper);
    }

    @Override
    public int hashCode() {
        int result = lower.hashCode();
        result = 31 * result + upper.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[" + lower + ":" + upper + "]";
    }

}
