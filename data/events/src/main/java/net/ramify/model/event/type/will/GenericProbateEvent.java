package net.ramify.model.event.type.will;

import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class GenericProbateEvent extends AbstractPersonEvent<GenericProbateEvent> implements ProbateEvent {

    public GenericProbateEvent(EventId id, PersonId personId, EventProperties properties) {
        super(id, personId, properties);
    }

    @Nonnull
    @Override
    public ProbateEvent with(final Place place) {
        return this;
    }

}
