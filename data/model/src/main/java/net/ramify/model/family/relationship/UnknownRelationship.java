package net.ramify.model.family.relationship;

import net.ramify.model.person.HasPerson;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class UnknownRelationship extends AbstractRelationship {

    public UnknownRelationship(final PersonId from, final PersonId to) {
        super(from, to);
    }

    public static Set<Relationship> between(final PersonId core, final Set<? extends HasPerson> people) {
        final Set<Relationship> relationships = new HashSet<>();
        people.forEach(person -> relationships.add(new UnknownRelationship(core, person.personId())));
        return relationships;
    }

    @Nonnull
    @Override
    public UnknownRelationship inverse() {
        return this;
    }

    @Override
    public Relationship replace(final PersonId from, final PersonId to) {
        return new UnknownRelationship(from, to);
    }

}
