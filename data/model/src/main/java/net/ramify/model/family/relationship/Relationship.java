package net.ramify.model.family.relationship;

import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Relationship between exactly two people.
 */
public interface Relationship {

    @Nonnull
    Person from();

    @Nonnull
    Person to();

    @Nonnull
    default Stream<Person> peopleStream() {
        return Stream.of(this.from(), this.to());
    }

    @Nonnull
    default Set<Person> people() {
        return Set.of(this.from(), this.to());
    }

    default boolean hasExact(final Person person) {
        return Objects.equals(this.from(), person) || Objects.equals(this.to(), person);
    }

    @Nonnull
    Relationship inverse();

    Relationship replace(Person from, Person to);

}
