package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class ParentChild extends AbstractRelationship {

    public ParentChild(final Person parent, final Person child) {
        super(parent, child);
    }

    @Nonnull
    public Person parent() {
        return this.from();
    }

    @Nonnull
    public Person child() {
        return this.to();
    }

    @Nonnull
    @Override
    public ChildParent inverse() {
        return new ChildParent();
    }

    @Override
    public ParentChild replace(final Person from, final Person to) {
        return new ParentChild(from, to);
    }

    @CheckForNull
    public Person parentOf(final Person child) {
        if (Objects.equals(this.child(), child)) {
            return this.parent();
        }
        return null;
    }

    @CheckForNull
    public Person childOf(final Person parent) {
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
        public Person from() {
            return this.child();
        }

        @Nonnull
        public Person child() {
            return ParentChild.this.child();
        }

        @Nonnull
        @Override
        public Person to() {
            return this.parent();
        }

        @Nonnull
        public Person parent() {
            return ParentChild.this.parent();
        }

        @Nonnull
        @Override
        public Stream<Person> peopleStream() {
            return ParentChild.this.peopleStream();
        }

        @Nonnull
        @Override
        public ParentChild inverse() {
            return ParentChild.this;
        }

        @Override
        public ChildParent replace(final Person from, final Person to) {
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
