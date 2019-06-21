package net.ramify.model.place.region;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Region;

public class Village extends AbstractSettlement {

    public Village(final PlaceId id, final String name, final Region region) {
        super(id, name, region);
    }

}
