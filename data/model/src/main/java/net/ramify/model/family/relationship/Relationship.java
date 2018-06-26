package net.ramify.model.family.relationship;

import net.ramify.model.Castable;
import net.ramify.model.person.Person;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Relationship between exactly two people.
 *
 * @see Relationships
 * @see PersonalRelationships
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

    @Nonnull
    @CheckReturnValue
    Relationship replace(Person from, Person to);

    default <R extends Relationship> Optional<R> as(final Class<R> clazz) {
        return Castable.cast(this, clazz);
    }

}
