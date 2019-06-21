package net.ramify.model.place.type;

import net.ramify.model.place.Place;

public interface PlaceHandler<R> {

    default R handle(final Place place) {
        return place.handleWith(this);
    }

    R handle(Country country);

    R handle(Region region);

    R handle(Settlement settlement);

    R handle(Building building);

}
