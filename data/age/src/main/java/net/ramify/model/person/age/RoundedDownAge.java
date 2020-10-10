package net.ramify.model.person.age;

import javax.annotation.Nonnull;
import java.time.Period;
import java.util.Optional;

class RoundedDownAge implements Age {

    private final int years;

    RoundedDownAge(final int years) {
        this.years = years;
    }

    @Nonnull
    @Override
    public Period lowerBound() {
        return Period.of(years, 0, 0);
    }

    @Nonnull
    @Override
    public Period upperBound() {
        return Period.of(years + 1, 0, -1);
    }

    @Nonnull
    @Override
    public Optional<Period> exact() {
        return Optional.empty();
    }

}
