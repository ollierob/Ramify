package net.ramify.model.event;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * Set of events with helper methods.
 */
public interface Events {

    @Nonnull
    Set<Event> events();

    @Nonnull
    default SortedSet<Event> events(final Comparator<? super Event> comparator) {
        final SortedSet<Event> events = new TreeSet<>(comparator);
        events.addAll(this.events());
        return events;
    }

    default Stream<Event> eventStream() {
        return this.events().stream();
    }

    default <E extends Event> Stream<E> eventStream(final Class<E> clazz) {
        return this.eventStream()
                .map(event -> event.cast(clazz))
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    default Optional<Event> earliest() {
        return this.earliest(Event.class);
    }

    @Nonnull
    default <E extends Event> Optional<E> earliest(final Class<E> clazz) {
        return this.earliest(clazz, Event.COMPARE_BY_DATE);
    }

    @Nonnull
    default <E extends Event> Optional<E> earliest(final Class<E> clazz, final Comparator<? super Event> comparator) {
        return this.eventStream(clazz).min(comparator);
    }

    @Nonnull
    default <E extends Event> Optional<E> latest(final Class<E> clazz) {
        return this.latest(clazz, Event.COMPARE_BY_DATE);
    }


    @Nonnull
    default <E extends Event> Optional<E> latest(final Class<E> clazz, final Comparator<? super Event> comparator) {
        return this.eventStream(clazz).max(comparator);
    }

    Events NONE = Set::of;

}
