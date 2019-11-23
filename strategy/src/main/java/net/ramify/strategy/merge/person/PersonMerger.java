package net.ramify.strategy.merge.person;

import net.ramify.model.person.Person;
import net.ramify.strategy.merge.Merger;

public interface PersonMerger extends Merger<Person, Person> {

    @Override
    default Person just(final Person person) {
        return person;
    }

}
