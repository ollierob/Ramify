package net.ramify.model.event.collection;

import net.ramify.model.event.PersonEvent;

import java.util.Set;

/**
 * @see net.ramify.model.person.Person
 */
public interface EventSet extends Set<PersonEvent>, HasEvents {

    @Override
    @Deprecated
    default Set<? extends PersonEvent> events() {
        return this;
    }

}
