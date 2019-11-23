package net.ramify.strategy.person.merge.name;

import net.ramify.model.person.name.Name;
import net.ramify.strategy.merge.Merger;

import javax.annotation.Nonnull;

public interface NameMerger<N extends Name> extends Merger<N, Name> {

    @Nonnull
    @Override
    Result<Name> merge(N n1, N n2);

    @Override
    default Name just(final N name) {
        return name;
    }

}
