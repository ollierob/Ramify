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

    default DateRange birthDate(final ExactDate exactDate) {
        final var date = exactDate.date();
        final var latest = date.minus(this.lowerBound());
        final var earliest = date.minus(this.upperBound());
        return DateRange.between(earliest, latest);
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
