package net.ramify.model.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

public class Village extends AbstractSettlement {

    public Village(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class), groupId);
    }

    public Village(final PlaceId id, final String name, final Region region, final PlaceGroupId groupId) {
        super(id, name, region, groupId);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.VILLAGE;
    }

}
