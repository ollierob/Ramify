package net.ramify.model.event.type.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GenericResidence extends AbstractEvent<GenericResidence> implements Residence {

    private final Place place;

    public GenericResidence(
            @Nonnull final PersonId personId,
            @Nonnull final DateRange date,
            @Nonnull final Place place) {
        super(personId, date);
        this.place = Objects.requireNonNull(place, "residence place");
    }

    @Nonnull
    @Override
    public Place place() {
        return place;
    }

    @Nonnull
    @Override
    public String title() {
        return "Residence";
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return Residence.super.toProtoBuilder();
    }

}
