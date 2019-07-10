package net.ramify.model.place;

import net.ramify.model.Id;
import net.ramify.model.place.provider.PlaceProvider;

import java.util.Objects;

public class PlaceId extends Id implements HasPlaceId {

    public PlaceId(final String value) {
        super(value);
    }

    @Override
    public PlaceId placeId() {
        return this;
    }

    public boolean isParentOf(final PlaceId that, final PlaceProvider placeProvider) {
        final var thisPlace = placeProvider.get(this);
        if (thisPlace == null) return false;
        final var thatPlace = placeProvider.get(that);
        if (thatPlace == null) return false;
        return thisPlace.isParentOf(thatPlace);
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
