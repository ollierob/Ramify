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
    default Optional<Period> exact() {
        final var lower = this.lowerBound().normalized();
        final var upper = this.upperBound().normalized();
        return lower.equals(upper) ? Optional.of(upper) : Optional.empty();
    }

    default DateRange birthDate(final DateRange date) {
        final var earliest = date.earliestInclusive().map(d -> d.minus(this.upperBound()));
        final var latest = date.latestInclusive().map(d -> d.minus(this.lowerBound()));
        return new AgeDateRange(earliest, latest);
    }

    static Age exactly(final Period period) {
        return new PeriodBasedAge(period);
    }

    static Age ofYears(final int years) {
        return new RoundedDownAge(years);
    }

    static Age between(final int minYears, final int maxYears) {
        if (minYears > maxYears) return between(maxYears, minYears);
        if (minYears == maxYears) return exactly(Period.ofYears(minYears));
        return new PeriodBasedAge(Period.ofYears(minYears), Period.ofYears(maxYears));
    }

    static DateRange birthDate(final int age, final DateRange date) {
        return Age.ofYears(age).birthDate(date);
    }

}
