package net.ramify.model.event.type.birth;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class GenericBaptism extends AbstractEvent<GenericBaptism> implements Baptism {

    public GenericBaptism(final PersonId personId, final DateRange date) {
        super(personId, date);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return Baptism.super.toProtoBuilder();
    }

    @Nonnull
    @Override
    public Baptism with(final Place place) {
        return place == null ? this : new BaptismWithPlace(this, place);
    }

}
