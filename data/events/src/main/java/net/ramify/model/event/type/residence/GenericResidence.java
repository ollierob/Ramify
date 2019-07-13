package net.ramify.model.event.type.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.AbstractEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GenericResidence extends AbstractEvent<GenericResidence> implements LifeEvent, HasPlaceId {

    private final PlaceId placeId;

    public GenericResidence(
            @Nonnull final PersonId personId,
            @Nonnull final DateRange date,
            @Nonnull final PlaceId placeId) {
        super(personId, date);
        this.placeId = Objects.requireNonNull(placeId);
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return placeId;
    }

    @Nonnull
    @Override
    public String title() {
        return "Residence";
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return super.toProtoBuilder()
                .setType(EventProto.RecordType.RESIDENCE);
    }

}
