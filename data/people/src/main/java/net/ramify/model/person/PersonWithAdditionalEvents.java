package net.ramify.model.person;

import com.google.common.collect.Sets;
import net.ramify.model.event.Event;

import java.util.Collections;
import java.util.Set;

public class PersonWithAdditionalEvents extends DelegatedPerson {

    private final Set<? extends Event> events;

    public PersonWithAdditionalEvents(final Person person, final Event event) {
        this(person, Collections.singleton(event));
    }

    public PersonWithAdditionalEvents(final Person person, final Set<? extends Event> events) {
        super(person);
        this.events = events;
    }

    @Override
    public Set<? extends Event> events() {
        return Sets.union(super.events(), events);
    }

}
