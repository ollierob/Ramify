package net.ramify.strategy.person.merge.name;

import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.person.name.Name;
import net.ramify.strategy.merge.Merger;

import javax.annotation.Nonnull;
import java.util.function.BiPredicate;

public class ForenameSurnameMerger implements NameMerger<ForenameSurname> {

    private final BiPredicate<String, String> surnamesMatch;

    public ForenameSurnameMerger(final BiPredicate<String, String> surnamesMatch) {
        this.surnamesMatch = surnamesMatch;
    }

    @Nonnull
    @Override
    public Result<Name> merge(final ForenameSurname n1, final ForenameSurname n2) {

        if (!surnamesMatch.test(n1.surname(), n2.surname())) return Merger.impossible();

        throw new UnsupportedOperationException(); //TODO
        
    }

}
