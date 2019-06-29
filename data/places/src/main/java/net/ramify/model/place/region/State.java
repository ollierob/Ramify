package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;

public class State extends AbstractRegion {

    private final Country country;

    public State(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Country.class));
    }

    public State(final PlaceId id, final String name, final Country country) {
        super(id, name);
        this.country = country;
    }

    @Nonnull
    @Override
    public Country parent() {
        return country;
    }

}
