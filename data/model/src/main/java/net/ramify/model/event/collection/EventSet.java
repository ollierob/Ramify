package net.ramify.model.event.collection;

import net.ramify.model.event.Event;

import java.util.Set;

/**
 * @param <E>
 * @see net.ramify.model.person.Person
 */
public interface EventSet<E extends Event> extends Set<E>, HasEvents {

    @Override
    @Deprecated
    default Set<? extends Event> events() {
        return this;
    }

}
