package net.ramify.model.family;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class SinglePersonFamily implements Family, HasPersonId {

    private final Person person;

    public SinglePersonFamily(final Person person) {
        this.person = person;
    }

    public Person person() {
        return person;
    }

    @Nonnull
    @Override
    public Person root() {
        return person;
    }

    @Nonnull
    @Override
    public Set<? extends Person> people() {
        return Collections.singleton(person);
    }

    @Nonnull
    @Override
    public Set<Relationship> relationships() {
        return Collections.emptySet();
    }

    @Nonnull
    @Override
    public PersonId personId() {
        return person.personId();
    }

    @Nonnull
    @Override
    public Set<PersonId> personIds() {
        return HasPersonId.super.personIds();
    }

    @Nonnull
    @Override
    public Optional<Relationship> relationshipBetween(PersonId from, PersonId to) {
        return Optional.empty();
    }

}
