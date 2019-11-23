package net.ramify.strategy.person.merge.name;

import net.ramify.model.person.name.ForenameSurname;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;

public class DefaultNameMerger implements NameMerger<Name> {

    private final ForenameSurnameMerger forenameSurnameMerger;

    public DefaultNameMerger(final ForenameSurnameMerger forenameSurnameMerger) {
        this.forenameSurnameMerger = forenameSurnameMerger;
    }

    @Nonnull
    @Override
    public Result<Name> merge(final Name n1, final Name n2) {
        if (n1 instanceof ForenameSurname && n2 instanceof ForenameSurname) return forenameSurnameMerger.merge((ForenameSurname) n1, (ForenameSurname) n2);
        throw new UnsupportedOperationException(); //TODO
    }

}
