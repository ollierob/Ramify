package net.ramify.strategy.match.person.name;

import net.ramify.model.person.name.Name;
import net.ramify.strategy.match.MatchResult;
import net.ramify.strategy.merge.person.name.NameMerger;

import javax.annotation.Nonnull;

public class MergedNameMatcher implements NameMatcher {

    private final NameMerger<Name> nameMerger;

    public MergedNameMatcher(final NameMerger<Name> nameMerger) {
        this.nameMerger = nameMerger;
    }

    @Nonnull
    @Override
    public MatchResult match(final Name t1, final Name t2) {
        final var result = nameMerger.merge(t1, t2);
        if (result.isImpossibleMerge()) return MatchResult.NO;
        if (result.isMergeable()) return MatchResult.YES;
        return MatchResult.PASS;
    }

}
