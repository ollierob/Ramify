package net.ramify.model.place;

import net.ramify.model.Id;

public abstract class PlaceId extends Id implements HasPlaceId {

    protected PlaceId(final String value) {
        super(value);
    }

    @Deprecated
    @Override
    public PlaceId placeId() {
        return this;
    }

}
