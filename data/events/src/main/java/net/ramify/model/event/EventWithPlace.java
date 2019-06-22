package net.ramify.model.event;

import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;

public class EventWithPlace<E extends Event> extends ComposedEvent<E> implements HasPlaceId {

    private final PlaceId placeId;

    public EventWithPlace(final E delegate, final PlaceId placeId) {
        super(delegate);
        this.placeId = placeId;
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return placeId;
    }

}
