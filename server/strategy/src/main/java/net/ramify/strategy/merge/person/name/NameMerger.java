package net.ramify.strategy.merge.person.name;

import net.ramify.model.person.name.Name;
import net.ramify.model.strategy.MergeResult;
import net.ramify.model.strategy.Merger;

import javax.annotation.Nonnull;

public interface NameMerger<N extends Name> extends Merger<N, Name> {

    @Nonnull
    @Override
    MergeResult<Name> merge(N n1, N n2);

    @Override
    default Name just(final N name) {
        return name;
    }

}
