package net.ramify.model.place.region.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.region.County;

public class RuralDistrict extends AbstractRegion implements District {

    private final County parent;

    public RuralDistrict(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(County.class), groupId);
    }

    public RuralDistrict(final PlaceId id, final String name, final County parent, final PlaceGroupId groupId) {
        super(id, name, groupId);
        this.parent = parent;
    }

    @Override
    public County parent() {
        return parent;
    }

}
