package net.ramify.model.event.type;

import net.ramify.model.event.PersonEvent;
import net.ramify.model.person.HasPersonId;

import javax.annotation.Nonnull;

public interface UniqueEvent extends PersonEvent, HasPersonId {

    @Nonnull
    Class<? extends UniqueEvent> uniqueType();

}
