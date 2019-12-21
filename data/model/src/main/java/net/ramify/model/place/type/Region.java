package net.ramify.model.place.type;

import net.ramify.model.place.Place;

public interface Region extends SettlementOrRegion {

    @Override
    Region parent();

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

    static Region cast(final Place place) throws InvalidPlaceTypeException {
        return place == null ? null : place.requireAs(Region.class);
    }

}
