package net.ramify.model.event.type.misc;

import net.ramify.model.event.Event;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.Place;

import javax.annotation.Nonnull;

public class EventWithPlace<E extends Event> extends ComposedEvent<E> implements HasPlace {

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

}
