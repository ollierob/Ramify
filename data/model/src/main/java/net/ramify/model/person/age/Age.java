package net.ramify.model.person.age;

import javax.annotation.Nonnull;
import java.time.Period;

public interface Age {

    Age ZERO = () -> Period.ZERO;

    static Age months(final int months) {
        return () -> Period.ofMonths(months);
    }

    static Age years(final int years) {
        return () -> Period.ofYears(years);
    }

    @Nonnull
    Period toPeriod();

}
