package net.ramify.model.family;

import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.AbstractRelationship;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.UnknownRelationship;

import javax.annotation.Nonnull;
import java.util.Set;

public class UnknownRelationshipFamily implements Family {

    private final Set<? extends Person> people;

    public UnknownRelationshipFamily(final Set<? extends Person> people) {
        this.people = people;
    }

    @Nonnull
    @Override
    public Set<? extends Person> people() {
        return people;
    }

    @Nonnull
    @Override
    public Set<? extends Relationship> relationships() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public boolean hasDirected() {
        return false;
    }

    private static class N extends AbstractRelationship implements UnknownRelationship {

        protected N(final PersonId from, final PersonId to) {
            super(from, to);
        }

    }

}
