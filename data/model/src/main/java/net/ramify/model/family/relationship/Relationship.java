package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Relationship {

    @Nonnull
    Stream<Person> peopleStream();

    @Nonnull
    default Set<Person> people() {
        return this.peopleStream().collect(Collectors.toSet());
    }

    @Nonnull
    Relationship inverse();

}
