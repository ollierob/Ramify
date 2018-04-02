package net.ramify.model.family;

import net.ramify.model.family.relationship.Relationship;
import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
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

    static Family of(final Relationship relationship) {
        return new SingleRelationshipFamily(relationship);
    }

}
