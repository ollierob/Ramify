package net.ramify.strategy.merge.person.feature;

import net.ramify.model.person.feature.Literacy;
import net.ramify.model.strategy.MergeResult;

import javax.annotation.Nonnull;

public class LiteracyMerger implements PersonFeatureMerger<Literacy> {

    private final boolean strict;

    public LiteracyMerger(final boolean strict) {
        this.strict = strict;
    }

    @Nonnull
    @Override
    public MergeResult<Literacy> merge(final Literacy f1, final Literacy f2) {
        if (f1.isLiterate() == f2.isLiterate()) return MergeResult.of(f1);
        return strict ? MergeResult.impossible() : MergeResult.unknown();
    }

}
