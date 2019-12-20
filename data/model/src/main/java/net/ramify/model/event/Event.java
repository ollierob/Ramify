package net.ramify.model.event;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.HasPlace;

import javax.annotation.Nonnull;

public interface Event extends HasEventId, HasDate, BuildsProto<EventProto.Event> {

    @Nonnull
    String title();

    @Nonnull
    default EventProto.Event.Builder toProtoBuilder() {
        final var builder = EventProto.Event.newBuilder()
                .setId(this.eventId().value())
                .setTitle(this.title())
                .setDate(this.date().toProto())
                .setIsUnique(true);
        HasPlace.getPlace(this).ifPresent(place -> builder.setPlace(place.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default EventProto.Event toProto() {
        return this.toProtoBuilder().build();
    }

}
