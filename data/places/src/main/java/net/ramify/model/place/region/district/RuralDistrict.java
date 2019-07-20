package net.ramify.model.place.region.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.region.County;

public class RuralDistrict extends AbstractRegion implements District {

    private final County parent;

    public RuralDistrict(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(County.class));
    }

    public RuralDistrict(final PlaceId id, final String name, final County parent) {
        super(id, name);
        this.parent = parent;
    }

    @Override
    public County parent() {
        return parent;
    }

}
