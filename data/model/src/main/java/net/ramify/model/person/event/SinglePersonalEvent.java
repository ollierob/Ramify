package net.ramify.model.person.event;

import net.ramify.model.event.Event;

import javax.annotation.Nonnull;
import java.util.Set;

public class SinglePersonalEvent implements PersonalEvents {

    private final Event event;

    public SinglePersonalEvent(final Event event) {
        this.event = event;
    }

    @Nonnull
    @Override
    public Set<Event> events() {
        return Set.of(event);
    }

}
