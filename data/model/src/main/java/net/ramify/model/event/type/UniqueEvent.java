package net.ramify.model.event.type;

import net.ramify.model.event.Event;
import net.ramify.model.person.HasPersonId;

import javax.annotation.Nonnull;

public interface UniqueEvent extends Event, HasPersonId {

    @Nonnull
    Class<? extends UniqueEvent> uniqueType();

}
