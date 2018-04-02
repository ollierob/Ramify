package net.ramify.model.family;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.Set;

public class SingleRelationshipFamily implements Family {

    private final Relationship relationship;

    public SingleRelationshipFamily(final Relationship relationship) {
        this.relationship = relationship;
    }

    @Nonnull
    @Override
    public Set<Relationship> relationships() {
        return Set.of(relationship);
    }

    @Nonnull
    @Override
    public Set<Person> people() {
        return relationship.people();
    }

}
