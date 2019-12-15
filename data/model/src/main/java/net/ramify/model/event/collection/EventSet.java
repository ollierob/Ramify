package net.ramify.model.event.collection;

import net.ramify.model.event.Event;

import java.util.Set;

/**
 * @see net.ramify.model.person.Person
 */
public interface EventSet extends Set<Event>, HasEvents {

    @Override
    @Deprecated
    default Set<? extends Event> events() {
        return this;
    }

}
