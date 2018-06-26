package net.ramify.strategy.merge.person.gender;

import net.ramify.model.person.gender.Gender;

import javax.annotation.Nonnull;
import java.util.Optional;

class SameGender implements GenderMerge {

    @Nonnull
    @Override
    public Optional<Gender> merge(@Nonnull Gender t1, @Nonnull Gender t2) {
        return t1.is(t2) ? Optional.of(t1.isUnknown() ? t2 : t1) : Optional.empty();
    }

}
