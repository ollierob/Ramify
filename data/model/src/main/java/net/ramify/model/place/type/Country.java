package net.ramify.model.place.type;

import net.ramify.model.place.Place;

public interface Country extends Place {

    @Deprecated
    @Override
    default Country country() {
        return this;
    }

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

}
