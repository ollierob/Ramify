package net.ramify.model.family.relationship;

import net.ramify.model.person.PersonId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ParentChild extends AbstractRelationship {

    public ParentChild(final PersonId parent, final PersonId child) {
        super(parent, child);
    }

    @Nonnull
    public PersonId parent() {
        return this.from();
    }

    @Nonnull
    public PersonId child() {
        return this.to();
    }

    @Nonnull
    @Override
    public ChildParent inverse() {
        return new ChildParent();
    }

    @Override
    public ParentChild replace(final PersonId from, final PersonId to) {
        return new ParentChild(from, to);
    }

    @CheckForNull
    public PersonId parentOf(final PersonId child) {
        if (Objects.equals(this.child(), child)) {
            return this.parent();
        }
        return null;
    }

    @CheckForNull
    public PersonId childOf(final PersonId parent) {
        if (Objects.equals(this.parent(), parent)) {
            return this.child();
        }
        return null;
    }

    @Override
    public <R extends Relationship> Optional<R> as(final Class<R> clazz) {
        if (clazz == ParentChild.class) {
            return (Optional<R>) Optional.of(this);
        }
        if (clazz == ChildParent.class) {
            return (Optional<R>) Optional.of(this.inverse());
        }
        return super.as(clazz);
    }

    public class ChildParent implements Relationship {

        private ChildParent() {
        }

        @Nonnull
        @Override
        public PersonId from() {
            return this.child();
        }

        @Nonnull
        public PersonId child() {
            return ParentChild.this.child();
        }

        @Nonnull
        @Override
        public PersonId to() {
            return this.parent();
        }

        @Nonnull
        public PersonId parent() {
            return ParentChild.this.parent();
        }

        @Nonnull
        @Override
        public Stream<PersonId> peopleStream() {
            return ParentChild.this.peopleStream();
        }

        @Nonnull
        @Override
        public ParentChild inverse() {
            return ParentChild.this;
        }

        @Override
        public ChildParent replace(final PersonId from, final PersonId to) {
            return new ParentChild(to, from).inverse();
        }

        @Override
        public <R extends Relationship> Optional<R> as(final Class<R> clazz) {
            if (clazz == ParentChild.class) {
                return (Optional<R>) Optional.of(this.inverse());
            }
            if (clazz == ChildParent.class) {
                return (Optional<R>) Optional.of(this);
            }
            return Relationship.super.as(clazz);
        }
    }

}
