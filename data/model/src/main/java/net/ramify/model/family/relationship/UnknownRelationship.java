package net.ramify.model.family.relationship;

import net.ramify.model.person.HasPerson;
import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class UnknownRelationship extends AbstractRelationship {

    public static Set<Relationship> between(final Person core, final Set<? extends HasPerson> people) {
        final Set<Relationship> relationships = new HashSet<>();
        people.forEach(person -> relationships.add(new UnknownRelationship(core, person.person())));
        return relationships;
    }

    public UnknownRelationship(final Person from, final Person to) {
        super(from, to);
    }

    @Nonnull
    @Override
    public UnknownRelationship inverse() {
        return this;
    }

    @Override
    public Relationship replace(final Person from, final Person to) {
        return new UnknownRelationship(from, to);
    }

}
