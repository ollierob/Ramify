package net.ramify.model.event.type.marriage;

import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.PersonEventWithProperties;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class GenericDivorceEvent extends AbstractPersonEvent<GenericDivorceEvent> implements PersonEventWithProperties, LifeEvent, DivorceEvent {

    public GenericDivorceEvent(
            final EventId eventId,
            final PersonId personId,
            final EventProperties properties) {
        super(eventId, personId, properties);
    }

    @Nonnull
    @Override
    public DivorceEvent with(final Place place) {
        return place == null ? this : new DivorceWithPlace(this, place);
    }

}
