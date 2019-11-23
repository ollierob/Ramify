package net.ramify.strategy.person.merge.name;

import net.ramify.model.person.Person;
import net.ramify.model.person.name.Name;

import javax.annotation.Nonnull;

public class GenderNamesMerger implements PersonNamesMerger {

    @Nonnull
    @Override
    public Result<Name> merge(final Person f1, final Person f2) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public Name just(Person from) {
        throw new UnsupportedOperationException(); //TODO
    }

}
