package net.ramify.model.person.age;

import javax.annotation.Nonnull;
import java.time.Period;

public interface AgeRange {

    @Nonnull
    Period min();

    @Nonnull
    Period max();

    static AgeRange ofYears(final int years) {
        return Age.ofYears(years);
    }

}
