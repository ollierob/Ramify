package net.ramify.model.person.age;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;

import javax.annotation.Nonnull;
import java.time.Period;

public interface Age {

    @Nonnull
    Period lowerBound();

    @Nonnull
    Period upperBound();

    default DateRange birthDate(final ExactDate date) {
        throw new UnsupportedOperationException();
    }

    static Age exactly(final Period period) {
        return new PeriodBasedAge(period);
    }

    static Age ofYears(final int years) {
        return new PeriodBasedAge(Period.ofYears(years));
    }

    static Age between(final int minYears, final int maxYears) {
        if (minYears > maxYears) return between(maxYears, minYears);
        throw new UnsupportedOperationException(); //TODO
    }

}
