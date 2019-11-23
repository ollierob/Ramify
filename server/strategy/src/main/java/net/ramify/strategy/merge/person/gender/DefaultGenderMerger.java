package net.ramify.strategy.merge.person.gender;

import net.ramify.model.person.gender.Gender;

import javax.annotation.Nonnull;

public class DefaultGenderMerger implements GenderMerger {

    @Nonnull
    @Override
    public Result<Gender> merge(final Gender g1, final Gender g2) {
        if (g1.isUnknown()) return Result.of(g2);
        if (g2.isUnknown()) return Result.of(g1);
        return g1.equals(g2) ? Result.of(g1) : Result.impossible();
    }

}
