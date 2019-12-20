package net.ramify.model.event.type.misc;

import net.ramify.model.event.PersonEvent;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public abstract class EventWithPlace<E extends PersonEvent> extends ComposedEvent<E> implements HasPlace {

    private final Place place;

    public EventWithPlace(final E delegate, final Place place) {
        super(delegate);
        this.place = place;
    }

    @Nonnull
    @Override
    public Place place() {
        return place;
    }

    @Nonnull
    @Override
    public EventProto.Event.Builder toProtoBuilder() {
        return super.toProtoBuilder().setPlace(place.toProto());
    }

}
