package net.ramify.model.place.building;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.BuildingHistory;
import net.ramify.model.place.type.SettlementOrRegion;

import javax.annotation.CheckForNull;

public class House extends AbstractBuilding {

    private final SettlementOrRegion parent;

    public House(final PlaceId id, final String name, final SettlementOrRegion parent, final PlaceGroupId groupId, final BuildingHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
    }

    @CheckForNull
    @Override
    public SettlementOrRegion parent() {
        return parent;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.HOUSE;
    }

}
