package net.ramify.model.person.age;

import net.ramify.model.date.DateRange;

import javax.annotation.Nonnull;
import java.time.Period;
import java.util.Optional;

public interface Age {

    Age ZERO = exactly(Period.ZERO);

    @Nonnull
    Period lowerBound();

    @Nonnull
    Period upperBound();

    @Nonnull
    Optional<Period> exact();

    default DateRange birthDate(final DateRange date) {
        final var latest = date.earliestInclusive().map(d -> d.minus(this.lowerBound()));
        final var earliest = date.latestInclusive().map(d -> d.plus(this.upperBound()));
        return new AgeDateRange(earliest, latest);
    }

    static Age exactly(final Period period) {
        return new PeriodBasedAge(period);
    }

    static Age ofYears(final int years) {
        return exactly(Period.ofYears(years));
    }

    static Age between(final int minYears, final int maxYears) {
        if (minYears > maxYears) return between(maxYears, minYears);
        throw new UnsupportedOperationException(); //TODO
    }

}
