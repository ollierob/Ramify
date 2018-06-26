package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @see net.ramify.model.event.PersonalEvents
 */
public interface PersonalRelationships extends Relationships {

    @Nonnull
    Person person();

    @Nonnull
    default Set<Person> parents() {
        return this.find(ParentChild.class)
                .map(relationship -> relationship.parentOf(this.person()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @Nonnull
    default Set<Person> children() {
        return this.find(ParentChild.class)
                .map(relationship -> relationship.childOf(this.person()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

}
