package net.ramify.model.place.region.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.region.County;

public class UrbanDistrict extends AbstractRegion implements District {

    private final County parent;

    public UrbanDistrict(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(County.class));
    }

    public UrbanDistrict(final PlaceId id, final String name, final County parent) {
        super(id, name);
        this.parent = parent;
    }

    @Override
    public County parent() {
        return parent;
    }

}
