package net.ramify.model.event;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public interface PersonEvents {

    @Nonnull
    Set<Event> events();

    @Nonnull
    default SortedSet<Event> events(final Comparator<? super Event> comparator) {
        final SortedSet<Event> events = new TreeSet<>(comparator);
        events.addAll(this.events());
        return events;
    }

}
