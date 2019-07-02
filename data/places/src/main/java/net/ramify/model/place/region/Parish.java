package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;

public class Parish extends AbstractRegion {

    private final Region parent;

    public Parish(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class));
    }

    public Parish(final PlaceId id, final String name, final Region parent) {
        super(id, name);
        this.parent = parent;
    }

    @Nonnull
    @Override
    public Region parent() {
        return parent;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.PARISH;
    }

}
