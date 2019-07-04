package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.Nonnull;

public class Chapelry extends AbstractRegion {

    private final Parish parent;

    public Chapelry(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Parish.class));
    }

    public Chapelry(final PlaceId id, final String name, final Parish parent) {
        super(id, name);
        this.parent = parent;
    }

    @Nonnull
    @Override
    public Parish parent() {
        return parent;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.CHAPELRY;
    }

}
