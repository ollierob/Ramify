package net.ramify.model.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Region;

public class Town extends AbstractSettlement {

    public Town(final PlaceId id, final String name, final Place parent) {
        this(id, name, parent.requireAs(Region.class));
    }

    public Town(final PlaceId id, final String name, final Region region) {
        super(id, name, region);
    }

}
