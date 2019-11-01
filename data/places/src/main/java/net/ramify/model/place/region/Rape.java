package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;

public class Rape extends Hundred implements CountyOrSubdivision {

    public Rape(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(County.class), groupId);
    }

    public Rape(final PlaceId id, final String name, final County parent, final PlaceGroupId groupId) {
        super(id, name, parent, groupId);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.RAPE;
    }

}
