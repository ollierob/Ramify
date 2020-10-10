package net.ramify.model.event;

import net.ollie.protobuf.BuildsProto;
import net.ramify.model.date.HasDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.HasPlace;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Event extends HasEventId, HasDate, BuildsProto<EventProto.Event> {

    @Nonnull
    String title();

    @Nonnull
    Optional<String> description();

    @Nonnull
    default EventProto.Event.Builder toProtoBuilder() {
        final var builder = EventProto.Event.newBuilder()
                .setId(this.eventId().value())
                .setTitle(this.title())
                .setDate(this.date().toProto())
                .setIsUnique(true);
        HasPlace.getPlace(this).ifPresent(place -> builder.setPlace(place.toProto()));
        this.description().ifPresent(builder::setDescription);
        return builder;
    }

    @Nonnull
    @Override
    default EventProto.Event toProto() {
        return this.toProtoBuilder().build();
    }

}
