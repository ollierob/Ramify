package net.ramify.model.person.collection;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.HasEvents;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.utils.collections.IterableUtils;
import net.ramify.utils.collections.SetUtils;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface HasPeople extends HasEvents, HasPersonIds {

    @Nonnull
    Set<? extends Person> people();

    default int numPeople() {
        return this.people().size();
    }

    default boolean has(@Nonnull final PersonId id) {
        return IterableUtils.any(this.people(), p -> id.equals(p.personId()));
    }

    @Nonnull
    default Optional<? extends Person> find(final PersonId id) {
        return IterableUtils.findFirst(this.people(), p -> id.equals(p.personId()));
    }

    @Nonnull
    @Override
    default Set<PersonId> personIds() {
        return SetUtils.transform(this.people(), Person::personId);
    }

    @Nonnull
    @Override
    default Set<? extends Event> events() {
        final var events = new HashSet<Event>();
        this.people().forEach(person -> events.addAll(person.events()));
        return events;
    }

    default <E extends Event> Set<E> events(final Class<E> eventType) {
        final var events = new HashSet<E>();
        this.people().forEach(person -> events.addAll(person.events(eventType)));
        return events;
    }

}
