package net.ramify.model.event;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.HasDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.Birth;
import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.event.type.PostDeathEvent;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.place.HasPlace;
import net.ramify.utils.objects.Castable;

import javax.annotation.Nonnull;

public interface Event extends HasDate, HasPersonId, Castable<Event>, BuildsProto<EventProto.Event> {

    @Nonnull
    DateRange date();

    <R> R handleWith(@Nonnull EventHandler<R> handler);

    default boolean isBirth() {
        return this.is(Birth.class);
    }

    default boolean isLife() {
        return this.is(LifeEvent.class);
    }

    default boolean isDeath() {
        return this.is(Death.class);
    }

    default boolean isPostDeath() {
        return this.is(PostDeathEvent.class);
    }

    @Nonnull
    String title();

    @Nonnull
    default EventProto.Event.Builder toProtoBuilder() {
        final var builder = EventProto.Event.newBuilder()
                .setTitle(this.title())
                .setDate(this.date().toProto());
        HasPlace.place(this).ifPresent(place -> builder.setPlace(place.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default EventProto.Event toProto() {
        return this.toProtoBuilder().build();
    }

}
