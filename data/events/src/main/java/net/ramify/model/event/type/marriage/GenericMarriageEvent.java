package net.ramify.model.event.type.marriage;

import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.PersonEventWithProperties;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class GenericMarriageEvent extends AbstractPersonEvent<GenericMarriageEvent> implements PersonEventWithProperties, LifeEvent, MarriageEvent {

    public GenericMarriageEvent(
            final EventId eventId,
            final PersonId personId,
            final EventProperties properties) {
        super(eventId, personId, properties);
    }

    @Override
    public MarriageEvent with(final Place place) {
        return place == null ? this : new GenericMarriageEventWithPlace(this.eventId(), this.personId(), this.properties(), place);
    }

    @Nonnull
    @Override
    public String title() {
        return "Marriage";
    }

}
