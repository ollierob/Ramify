package net.ramify.model.family;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.Set;

public class SinglePersonFamily implements Family {

    private final PersonId person;

    public SinglePersonFamily(final PersonId person) {
        this.person = person;
    }

    @Nonnull
    @Override
    public Set<Relationship> relationships() {
        return Set.of();
    }

    @Nonnull
    @Override
    public Set<PersonId> people() {
        return Set.of(person);
    }

}
