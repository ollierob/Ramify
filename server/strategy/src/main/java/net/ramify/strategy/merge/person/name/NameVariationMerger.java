package net.ramify.strategy.merge.person.name;

import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.Names;
import net.ramify.model.strategy.MergeResult;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;

public class NameVariationMerger implements NameMerger<Name> {

    private final BiPredicate<String, String> namesMatch;

    public NameVariationMerger(final BiPredicate<String, String> namesMatch) {
        this.namesMatch = namesMatch;
    }

    @Nonnull
    @Override
    public MergeResult<Name> merge(final Name n1, final Name n2) {

        if (n1.isUnknown()) return MergeResult.of(n2);
        if (n2.isUnknown()) return MergeResult.of(n1);

        for (final var v1 : n1.variations()) {
            for (final var v2 : n2.variations()) {
                if (namesMatch.test(v1, v2)) return MergeResult.of(Names.of(n1, n2));
            }
        }

        return MergeResult.impossible();

    }

}
