package net.ramify.model.person;

import net.ramify.model.event.Event;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.collection.PersonEventSet;

import java.util.Set;

public class PersonWithAdditionalEvents extends DelegatedPerson {

    private final Set<? extends Event> events;

    public PersonWithAdditionalEvents(final Person person, final Event event) {
        this(person, new MutablePersonEventSet(event));
    }

    public PersonWithAdditionalEvents(final Person person, final PersonEventSet events) {
        super(person);
        this.events = events;
    }

    @Override
    public PersonEventSet events() {
        //return Sets.union(super.events(), events);
        throw new UnsupportedOperationException(); //TODO
    }

}
