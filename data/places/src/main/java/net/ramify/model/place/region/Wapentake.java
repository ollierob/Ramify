package net.ramify.model.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;

public class Wapentake extends Hundred implements CountyOrSubdivision {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Wapentake");

    public Wapentake(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId, final PlaceHistory history) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(County.class), groupId, history);
    }

    public Wapentake(final PlaceId id, final String name, final County parent, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, parent, groupId, history);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }
    
}
