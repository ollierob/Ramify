package net.ramify.model.person.collection;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.HasEvents;
import net.ramify.model.person.Person;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public interface HasPeople extends HasEvents {

    @Nonnull
    Set<? extends Person> people();

    default boolean hasPerson(@Nonnull final Person person) {
        return this.people().contains(person);
    }

    @Nonnull
    @Override
    default Set<? extends Event> events() {
        final var events = new HashSet<Event>();
        this.people().forEach(person -> events.addAll(person.events()));
        return events;
    }

}
