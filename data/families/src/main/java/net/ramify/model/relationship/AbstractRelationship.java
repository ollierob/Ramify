package net.ramify.model.relationship;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class AbstractRelationship implements Relationship {

    private final PersonId from, to;

    protected AbstractRelationship(final HasPersonId from, final HasPersonId to) {
        this(Objects.requireNonNull(from, "from").personId(), Objects.requireNonNull(to, "to").personId());
    }

    protected AbstractRelationship(final PersonId from, final PersonId to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public PersonId fromId() {
        return from;
    }

    @Nonnull
    @Override
    public PersonId toId() {
        return to;
    }

    @Nonnull
    @Override
    public abstract String describeTo();

    @Nonnull
    @Override
    public Relationship inverse() {
        return new InverseRelationship<>(this);
    }

}

