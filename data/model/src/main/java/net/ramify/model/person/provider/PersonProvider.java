package net.ramify.model.person.provider;

import net.ramify.model.Provider;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;

public interface PersonProvider extends Provider<PersonId, Person> {

    @Nonnull
    PersonId createPerson();

}
