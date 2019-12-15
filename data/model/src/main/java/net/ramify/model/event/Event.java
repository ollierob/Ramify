package net.ramify.model.event;

import net.meerkat.objects.Castable;
import net.ramify.data.proto.BuildsProto;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.HasDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.EventHandler;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.event.type.PostDeathEvent;
import net.ramify.model.event.type.UniqueEvent;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.age.HasAges;
import net.ramify.model.place.HasPlace;

import javax.annotation.Nonnull;
import java.util.Optional;

public interface Event extends HasEventId, HasAges, HasDate, HasPersonId, Castable<Event>, BuildsProto<EventProto.Event> {

    @Nonnull
    String title();

    @Nonnull
    DateRange date();

    <R> R handleWith(@Nonnull EventHandler<R> handler);

    default boolean isBirth() {
        return this.is(BirthEvent.class);
    }

    default boolean isLife() {
        return this.is(LifeEvent.class);
    }

    default boolean isDeath() {
        return this.is(DeathEvent.class);
    }

    default boolean isPostDeath() {
        return this.is(PostDeathEvent.class);
    }

    default boolean isUnique() {
        return this.is(UniqueEvent.class);
    }

    @Nonnull
    Optional<String> occupation();

    boolean isInferred();

    @Nonnull
    default EventProto.Event.Builder toProtoBuilder() {
        final var builder = EventProto.Event.newBuilder()
                .setId(this.eventId().value())
                .setTitle(this.title())
                .setDate(this.date().toProto())
                .setIsUnique(this.isUnique())
                .setPersonId(this.personId().value())
                .setInferred(this.isInferred());
        HasPlace.place(this).ifPresent(place -> builder.setPlace(place.toProto()));
        this.givenAge().ifPresent(age -> builder.setGivenAge(age.toProto()));
        this.computedAge().ifPresent(age -> builder.setComputedAge(age.toProto()));
        this.occupation().ifPresent(builder::setOccupation);
        return builder;
    }

    @Nonnull
    @Override
    default EventProto.Event toProto() {
        return this.toProtoBuilder().build();
    }

}
