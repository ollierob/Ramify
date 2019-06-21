package net.ramify.model.place;

import net.ramify.model.Id;

public class PlaceId extends Id implements HasPlaceId {

    public PlaceId(final String value) {
        super(value);
    }

    @Deprecated
    @Override
    public PlaceId placeId() {
        return this;
    }

}
