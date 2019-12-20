package net.ramify.model.person;

import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.collection.PersonEventSet;
import net.ramify.model.event.collection.SingletonPersonEventSet;

public class PersonWithAdditionalEvents extends DelegatedPerson {

    private final PersonEventSet events;

    public PersonWithAdditionalEvents(final Person person, final PersonEvent event) {
        this(person, new SingletonPersonEventSet(event));
    }

    public PersonWithAdditionalEvents(final Person person, final PersonEventSet events) {
        super(person);
        this.events = events;
    }

    @Override
    public PersonEventSet events() {
        final var union = new MutablePersonEventSet(events);
        union.addAll(super.events());
        return union;
    }

}
