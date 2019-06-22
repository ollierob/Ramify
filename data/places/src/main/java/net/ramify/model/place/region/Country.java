package net.ramify.model.place.region;

import net.ramify.model.place.PlaceId;

public class Country extends AbstractRegion {

    public Country(PlaceId id, String name) {
        super(id, name);
    }

    @Override
    public Country parent() {
        return this;
    }

}
