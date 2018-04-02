package net.ramify.model.person.age;

import javax.annotation.Nonnull;
import java.time.Period;

public interface Age extends AgeRange {

    Age ZERO = () -> Period.ZERO;

    static Age days(final int days) {
        return () -> Period.ofDays(days);
    }

    static Age months(final int months) {
        return () -> Period.ofMonths(months);
    }

    static Age years(final int years) {
        return () -> Period.ofYears(years);
    }

    @Nonnull
    Period toPeriod();

    @Nonnull
    @Override
    default Period min() {
        return this.toPeriod();
    }

    @Nonnull
    @Override
    default Period max() {
        return this.toPeriod();
    }

}
