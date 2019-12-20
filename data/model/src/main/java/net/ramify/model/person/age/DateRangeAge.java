package net.ramify.model.person.age;

import net.ramify.model.date.DateRange;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.time.Period;

public class DateRangeAge implements Age {

    static Age of(final DateRange birthDate, final DateRange ageDate) {
        final var birthExact = birthDate.exact().orElse(null);
        final var dateExact = ageDate.exact().orElse(null);
        if (birthExact != null && dateExact != null) return new PeriodBasedAge(Period.between(LocalDate.from(birthExact), LocalDate.from(dateExact)));
        return new DateRangeAge(birthDate, ageDate);
    }

    private final Period lower, upper;

    DateRangeAge(final DateRange birthDate, final DateRange ageDate) {
        this.lower = period(birthDate.latestIsoDate(), ageDate.earliestIsoDate());
        this.upper = period(birthDate.earliestIsoDate(), ageDate.latestIsoDate());
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

    private static Period period(final LocalDate from, final LocalDate to) {
        if (from.isAfter(to)) return Period.ZERO;
        return Period.between(from, to);
    }

}
