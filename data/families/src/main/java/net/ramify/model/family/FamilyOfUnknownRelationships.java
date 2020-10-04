package net.ramify.model.family;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.Unknown;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class FamilyOfUnknownRelationships implements Family {

    private final Set<? extends Person> people;

    public FamilyOfUnknownRelationships(final Collection<? extends Person> people) {
        this(ImmutableSet.copyOf(people));
    }

    public FamilyOfUnknownRelationships(final Set<? extends Person> people) {
        this.people = people;
    }

    @Nonnull
    @Override
    public Set<? extends Person> people() {
        return people;
    }

    @Nonnull
    @Override
    public Person root() {
        return people.isEmpty() ? null : people.iterator().next();
    }

    @Nonnull
    @Override
    public Set<? extends Relationship> relationships() {
        return Collections.emptySet();
    }

    @Override
    public boolean hasDirected() {
        return false;
    }

    @Nonnull
    @Override
    public Optional<Relationship> relationshipBetween(final PersonId from, final PersonId to) {
        return Optional.of(new Unknown(from, to));
    }

}
