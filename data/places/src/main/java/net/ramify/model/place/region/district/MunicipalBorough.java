package net.ramify.model.place.region.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.type.Region;

public class MunicipalBorough extends AbstractRegion implements District {

    private final Region parent;

    public MunicipalBorough(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class));
    }

    public MunicipalBorough(final PlaceId id, final String name, final Region parent) {
        super(id, name);
        this.parent = parent;
    }

    @Override
    public Region parent() {
        return parent;
    }

}
