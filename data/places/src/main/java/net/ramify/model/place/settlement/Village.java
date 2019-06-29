package net.ramify.model.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Region;

public class Village extends AbstractSettlement {

    public Village(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class));
    }

    public Village(final PlaceId id, final String name, final Region region) {
        super(id, name, region);
    }

}
