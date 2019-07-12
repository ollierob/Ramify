package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;

public class Wapentake extends Hundred {

    public Wapentake(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(County.class));
    }

    public Wapentake(final PlaceId id, final String name, final County parent) {
        super(id, name, parent);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.WAPENTAKE;
    }

}
