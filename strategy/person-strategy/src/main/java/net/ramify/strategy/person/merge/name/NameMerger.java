package net.ramify.strategy.person.merge.name;

import net.ramify.model.person.name.Name;
import net.ramify.strategy.merge.Merger;

public interface NameMerger extends Merger<Name, Name> {

    @Override
    default Name just(final Name name) {
        return name;
    }

}
