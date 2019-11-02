package net.ramify.model.person.age;

import net.ramify.model.date.DateRange;

import javax.annotation.Nonnull;
import java.time.Period;
import java.util.Optional;

import static net.ramify.utils.time.PeriodUtils.approximateCompare;

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

    default boolean isSame(final Period period) {
        return approximateCompare(this.lowerBound(), period) <= 0
                && approximateCompare(period, this.upperBound()) <= 0;
    }

    default boolean isSameOrOlderThan(final Period period) {
        return approximateCompare(period, this.upperBound()) <= 0;
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

    static Age between(final Period min, final Period max) {
        return new PeriodBasedAge(min, max);
    }

    static DateRange birthDate(final int age, final DateRange on) {
        return Age.ofYears(age).birthDate(on);
    }

    static DateRange birthDate(final Period age, final DateRange on) {
        return Age.exactly(age).birthDate(on);
    }

}
