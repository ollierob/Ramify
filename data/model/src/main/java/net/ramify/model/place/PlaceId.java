package net.ramify.model.place;

import net.ramify.model.Id;

public class PlaceId extends Id implements HasPlaceId {

    protected PlaceId(final String value) {
        super(value);
    }

    @Override
    public PlaceId placeId() {
        return this;
    }

}
