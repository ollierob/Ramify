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

    public class ChildParent implements Relationship {

        private ChildParent() {
        }

        @Nonnull
        public Person child() {
            return ParentChild.this.child();
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

    }

}
