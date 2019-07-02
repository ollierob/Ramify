package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;

public class County extends AbstractRegion {

    private final Region parent;

    public County(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class));
    }

    public County(final PlaceId id, final String name, final Region parent) {
        super(id, name);
        this.parent = parent;
    }

    @Override
    public Region parent() {
        return parent;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.COUNTY;
    }

}
