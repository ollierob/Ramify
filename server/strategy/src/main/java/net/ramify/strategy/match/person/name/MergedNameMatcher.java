package net.ramify.strategy.match.person.name;

import net.meerkat.functions.optionals.OptionalBoolean;
import net.ramify.model.person.name.Name;
import net.ramify.strategy.merge.person.name.NameMerger;

import javax.annotation.Nonnull;

public class MergedNameMatcher implements NameMatcher {

    private final NameMerger<Name> nameMerger;

    public MergedNameMatcher(final NameMerger<Name> nameMerger) {
        this.nameMerger = nameMerger;
    }

    @Nonnull
    @Override
    public OptionalBoolean match(final Name t1, final Name t2) {
        return nameMerger.merge(t1, t2).isPresent();
    }

}
