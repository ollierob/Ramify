package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
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
    public Relationship replace(Person from, Person to) {
        return new ParentChild(from, to);
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

    }

}
