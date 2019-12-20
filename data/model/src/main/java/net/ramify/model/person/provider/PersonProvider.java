package net.ramify.model.person.provider;

import net.ramify.model.util.provider.Provider;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public interface PersonProvider extends Provider<PersonId, Person> {

    @Nonnull
    default PersonId createId() {
        return PersonId.random();
    }

}
