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

    private final DateRange birthDate;
    private final DateRange ageDate;

    DateRangeAge(DateRange birthDate, DateRange ageDate) {
        this.birthDate = birthDate;
        this.ageDate = ageDate;
    }

    @Nonnull
    @Override
    public Period lowerBound() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Nonnull
    @Override
    public Period upperBound() {
        throw new UnsupportedOperationException(); //TODO
    }

}
