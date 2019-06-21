package net.ramify.model.event;

import javax.annotation.Nonnull;

public interface HasEventId {

    @Nonnull
    EventId eventId();

}
