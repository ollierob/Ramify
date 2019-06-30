package net.ramify.model.place;

import net.ramify.model.Id;

import java.util.Objects;

public class PlaceId extends Id implements HasPlaceId {

    public PlaceId(final String value) {
        super(value);
    }

    @Override
    public PlaceId placeId() {
        return this;
    }

    @Override
    protected boolean equals(final Id that) {
        return that instanceof PlaceId
                && this.equals((PlaceId) that);
    }

    boolean equals(final PlaceId that) {
        return Objects.equals(this.value(), that.value());
    }

}
