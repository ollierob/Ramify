package net.ramify.model.family;

import net.ramify.model.family.relationship.MarriedCouple;
import net.ramify.model.family.relationship.ParentChild;
import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.family.relationship.UnknownRelationship;
import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public interface Family {

    Family EMPTY = Set::of;

    @Nonnull
    Set<Relationship> relationships();

    @Nonnull
    default Set<Person> people() {
        return this.relationships()
                .stream()
                .flatMap(Relationship::peopleStream)
                .collect(Collectors.toSet());
    }

    @Nonnull
    default Set<Relationship> involving(final Person person) {
        return this.relationships()
                .stream()
                .filter(relationship -> relationship.hasExact(person))
                .collect(Collectors.toSet());
    }

    static Family of(final Person person) {
        return new SinglePersonFamily(person);
    }

    static Family of(final Relationship relationship) {
        return of(Set.of(relationship));
    }

    static Family of(final Relationship... relationships) {
        return of(Set.of(relationships));
    }

    static Family of(final Set<Relationship> relationships) {
        return () -> relationships;
    }

    static Builder startingWith(final Person person) {
        return new Builder(person);
    }

    class Builder {

        private final Person person;

        private Builder(final Person person) {
            this.person = person;
        }

        public Family self() {
            return of(person);
        }

        public Family withParent(final Person parent) {
            return Family.of(new ParentChild(parent, person));
        }

        public Family withParents(final Person firstParent, final Person secondParent, final boolean spouses) {
            final Set<Relationship> relationships = new HashSet<>();
            relationships.add(new ParentChild(firstParent, person));
            relationships.add(new ParentChild(secondParent, person));
            relationships.add(spouses ? new MarriedCouple(firstParent, secondParent) : new UnknownRelationship(firstParent, secondParent));
            return Family.of(relationships);
        }

        public Family withGrandparent(final Person grandparent) {
            final Person unknownParent = new Person() {

            }; //FIXME
            return Family.of(new ParentChild(grandparent, unknownParent), new ParentChild(unknownParent, person));
        }

    }

}
