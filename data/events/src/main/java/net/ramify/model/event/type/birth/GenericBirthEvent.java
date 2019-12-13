package net.ramify.model.event.type.birth;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class GenericBirthEvent extends AbstractPersonEvent<GenericBirthEvent> implements BirthEvent {

    private final PersonId personId;

    public GenericBirthEvent(final EventId id, final PersonId personId, final DateRange date) {
        super(id, personId, date);
        this.personId = personId;
    }

    @Nonnull
    @Override
    public PersonId personId() {
        return personId;
    }

    @Override
    public BirthEvent with(final Place place) {
        return place == null ? this : new BirthWithPlace(this, place);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return BirthEvent.super.toProtoBuilder();
    }

}
