package net.ramify.model.event;

import net.ramify.model.Id;

public class EventId extends Id implements HasEventId {

    public EventId(final String value) {
        super(value);
    }

    @Deprecated
    @Override
    public EventId eventId() {
        return this;
    }

}
