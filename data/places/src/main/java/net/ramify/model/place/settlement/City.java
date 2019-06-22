package net.ramify.model.place.settlement;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Region;

public class City extends AbstractSettlement {

    public City(PlaceId id, String name, Region region) {
        super(id, name, region);
    }

}
