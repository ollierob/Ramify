package net.ramify.model.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

public class Hamlet extends AbstractSettlement {

    public Hamlet(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class));
    }

    public Hamlet(final PlaceId id, final String name, final Region region) {
        super(id, name, region);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.HAMLET;
    }

}