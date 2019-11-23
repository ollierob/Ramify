package net.ramify.strategy.person.merge.gender;

import net.ramify.model.person.gender.Gender;
import net.ramify.strategy.merge.Merger;

import javax.annotation.Nonnull;

public class DefaultGenderMerger implements GenderMerger {

    @Nonnull
    @Override
    public Result<Gender> merge(final Gender g1, final Gender g2) {
        if (g1.isUnknown()) return Merger.value(g2);
        if (g2.isUnknown()) return Merger.value(g1);
        return g1.equals(g2) ? Merger.value(g1) : Merger.impossible();
    }

}
