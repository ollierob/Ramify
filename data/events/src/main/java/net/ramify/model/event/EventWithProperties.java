package net.ramify.model.event;

import net.ramify.model.date.DateRange;
import net.ramify.model.person.age.Age;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface EventWithProperties extends PersonEvent {

    @Nonnull
    EventProperties properties();

    @Nonnull
    @Override
    default DateRange date() {
        return this.properties().date();
    }

    @Nonnull
    @Override
    default Optional<Age> givenAge() {
        return this.properties().givenAge();
    }

    @Nonnull
    @Override
    default Optional<Age> computedAge() {
        return this.properties().computedAge();
    }

    @Override
    default Optional<String> occupation() {
        return this.properties().occupation();
    }

    @Override
    default boolean isInferred() {
        return this.properties().isInferred();
    }

}
