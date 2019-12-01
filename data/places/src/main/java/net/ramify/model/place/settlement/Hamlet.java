package net.ramify.model.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

public class Hamlet extends AbstractSettlement {

    public Hamlet(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId, final PlaceHistory history) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class), groupId, history);
    }

    public Hamlet(final PlaceId id, final String name, final Region region, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, region, groupId, history);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.HAMLET;
    }

}
