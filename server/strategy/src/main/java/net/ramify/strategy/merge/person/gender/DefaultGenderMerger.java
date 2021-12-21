package net.ramify.strategy.merge.person.gender;

import net.ramify.model.person.gender.Gender;
import net.ramify.model.strategy.MergeResult;

import javax.annotation.Nonnull;

public class DefaultGenderMerger implements GenderMerger {

    @Nonnull
    @Override
    public MergeResult<Gender> merge(final Gender g1, final Gender g2) {
        if (g1.isUnknown()) return MergeResult.of(g2);
        if (g2.isUnknown()) return MergeResult.of(g1);
        return g1.equals(g2) ? MergeResult.of(g1) : MergeResult.impossible();
    }

}
