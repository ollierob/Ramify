package net.ramify.strategy.merge.person.feature;

import net.ramify.model.person.feature.Literacy;

import javax.annotation.Nonnull;

public class LiteracyMerger implements PersonFeatureMerger<Literacy> {

    private final boolean strict;

    public LiteracyMerger(final boolean strict) {
        this.strict = strict;
    }

    @Nonnull
    @Override
    public Result<Literacy> merge(final Literacy f1, final Literacy f2) {
        if (f1.isLiterate() == f2.isLiterate()) return Result.of(f1);
        return strict ? Result.impossible() : Result.unknown();
    }

}
