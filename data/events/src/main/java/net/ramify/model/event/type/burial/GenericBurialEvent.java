package net.ramify.model.event.type.burial;

import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class GenericBurialEvent extends AbstractPersonEvent<GenericBurialEvent> implements BurialEvent {

    private final Place burialPlace;

    public GenericBurialEvent(final EventId id, final PersonId personId, final EventProperties properties, final Place burialPlace) {
        super(id, personId, properties);
        this.burialPlace = burialPlace;
    }

    @Nonnull
    @Override
    public Place place() {
        return burialPlace;
    }

}
