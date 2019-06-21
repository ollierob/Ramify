package net.ramify.model.family;

import net.ramify.model.family.relationship.MarriedCouple;
import net.ramify.model.family.relationship.ParentChild;
import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.family.relationship.UnknownRelationship;
import net.ramify.model.person.PersonId;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Family {

    Family EMPTY = Set::of;

    @Nonnull
    Set<Relationship> relationships();

    static Family of(final PersonId person) {
        return new SinglePersonFamily(person);
    }

    static Builder startingWith(final PersonId person) {
        return new Builder(person);
    }

    @Nonnull
    default Stream<PersonId> peopleStream() {
        return this.relationships()
                .stream()
                .flatMap(Relationship::peopleStream);
    }

    @Nonnull
    default Set<PersonId> people() {
        return this.peopleStream().collect(Collectors.toSet());
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

    @Nonnull
    default Set<Relationship> involving(final PersonId person) {
        return this.relationships()
                .stream()
                .filter(relationship -> relationship.hasExact(person))
                .collect(Collectors.toSet());
    }

    class Builder {

        private final PersonId person;

        private Builder(final PersonId person) {
            this.person = person;
        }

        public Family self() {
            return of(person);
        }

        public Family withParent(final PersonId parent) {
            return Family.of(new ParentChild(parent, person));
        }

        public Family withParents(final PersonId firstParent, final PersonId secondParent, final boolean spouses) {
            final Set<Relationship> relationships = new HashSet<>();
            relationships.add(new ParentChild(firstParent, person));
            relationships.add(new ParentChild(secondParent, person));
            relationships.add(spouses ? new MarriedCouple(firstParent, secondParent) : new UnknownRelationship(firstParent, secondParent));
            return Family.of(relationships);
        }

        public Family withGrandparent(final PersonId grandparent) {
            final PersonId unknownParent = new PersonId() {

            }; //FIXME
            return Family.of(new ParentChild(grandparent, unknownParent), new ParentChild(unknownParent, person));
        }

    }

}
