package net.ramify.model.place.region.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;

import javax.annotation.Nonnull;

public class CivilParish extends AbstractRegion {

    private final District parent;

    public CivilParish(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(District.class));
    }

    public CivilParish(final PlaceId id, final String name, final District parent) {
        super(id, name);
        this.parent = parent;
    }

    @Override
    public District parent() {
        return parent;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.CIVIL_PARISH;
    }

}
