package net.ramify.model.event.collection;

import net.ramify.model.event.Event;

import java.util.Collection;
import java.util.HashSet;

public class MutablePersonEventSet extends HashSet<Event> implements PersonEventSet {

    public MutablePersonEventSet() {
    }

    public MutablePersonEventSet(final Event... events) {
        for (int i = 0; i < events.length; i++) {
            this.doAdd(events[i]);
        }
    }

    public MutablePersonEventSet(final Collection<? extends Event> events) {
        events.forEach(this::doAdd);
    }

    @Override
    public boolean add(final Event event) {
        return this.doAdd(event);
    }

    private boolean doAdd(final Event event) {
        return super.add(event);
    }

}
