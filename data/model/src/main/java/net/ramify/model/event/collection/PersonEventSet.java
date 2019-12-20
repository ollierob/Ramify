package net.ramify.model.event.collection;

import net.meerkat.collections.Iterables;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.UniqueEvent;
import net.ramify.model.person.age.Age;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Predicate;

public interface PersonEventSet extends EventSet, HasPersonEvents {

    @Override
    @Deprecated
    default PersonEventSet events() {
        return this;
    }

    @Nonnull
    default <T extends UniqueEvent> Optional<T> find(@Nonnull final Class<T> type) {
        return Iterables.findFirst(this.events(), e -> e.is(type)).flatMap(e -> e.as(type));
    }

    @Nonnull
    default Optional<BirthEvent> findBirth() {
        return this.find(BirthEvent.class);
    }

    @Nonnull
    default Optional<DeathEvent> findDeath() {
        return this.find(DeathEvent.class);
    }

    @Nonnull
    default Optional<Age> inferAge(@Nonnull final Event event) {
        if (event.isBirth()) return Optional.of(Age.ZERO);
        if (event.isPostDeath()) return Optional.empty();
        return this.findBirth().map(birth -> Age.fromDates(birth.date(), event.date()));
    }

    @Nonnull
    @CheckReturnValue
    PersonEventSet filteredCopy(@Nonnull Predicate<? super Event> predicate);

}
