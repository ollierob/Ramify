package net.ramify.model.place.region;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Region;

public class Country extends AbstractRegion {

    public Country(PlaceId id, String name) {
        super(id, name);
    }

    @Override
    public Region parent() {
        return null;
    }

}
