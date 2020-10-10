package net.ramify.model.event;

import net.ramify.model.Id;

import javax.annotation.Nonnull;
import java.util.UUID;

public class EventId extends Id {

    public EventId(@Nonnull final String value) {
        super(value);
    }

    @Deprecated
    public static EventId random() {
        return new EventId(UUID.randomUUID().toString());
    }

}
