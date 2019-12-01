package net.ramify.model.place.region.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.region.County;

public class RuralDistrict extends AbstractRegion implements District {

    public static final PlaceHistory HISTORY = UrbanDistrict.HISTORY;

    private final County parent;

    public RuralDistrict(
            final PlaceId id,
            final String name,
            final Place parent,
            final PlaceGroupId groupId,
            final PlaceHistory history) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(County.class), groupId, history);
    }

    public RuralDistrict(
            final PlaceId id,
            final String name,
            final County parent,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
    }

    @Override
    public County parent() {
        return parent;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.RURAL_DISTRICT;
    }

}
