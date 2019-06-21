package net.ramify.model.place.type;

import net.ramify.model.place.Place;

public interface Country extends Place {

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
