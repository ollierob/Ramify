package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public abstract class AbstractRelationship implements Relationship {

    private final Person from, to;

    protected AbstractRelationship(final Person from, final Person to) {
        this.from = from;
        this.to = to;
    }

    @Nonnull
    protected Person from() {
        return from;
    }

    @Nonnull
    protected Person to() {
        return to;
    }

    @Nonnull
    @Override
    public Stream<Person> peopleStream() {
        return Stream.of(from, to).distinct();
    }

}
