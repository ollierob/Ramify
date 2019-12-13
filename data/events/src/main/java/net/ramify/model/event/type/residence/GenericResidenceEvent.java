package net.ramify.model.event.type.residence;

import net.ramify.model.event.AbstractPersonEvent;
import net.ramify.model.event.EventId;
import net.ramify.model.event.EventProperties;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;
import java.util.Objects;

public class GenericResidenceEvent extends AbstractPersonEvent<GenericResidenceEvent> implements ResidenceEvent {

    private final Place place;

    public GenericResidenceEvent(
            @Nonnull final EventId id,
            @Nonnull final PersonId personId,
            final EventProperties properties,
            @Nonnull final Place place) {
        super(id, personId, properties);
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
    @Deprecated
    public GenericResidenceEvent with(final Place place) {
        return new GenericResidenceEvent(this.eventId(), this.personId(), this.properties(), place);
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return ResidenceEvent.super.toProtoBuilder();
    }

}
