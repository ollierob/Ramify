package net.ramify.model.place.building;

import net.ramify.model.place.AbstractPlace;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Building;
import net.ramify.model.place.type.SettlementOrRegion;

import javax.annotation.CheckForNull;

public class House extends AbstractPlace implements Building {

    private final SettlementOrRegion parent;

    public House(final PlaceId id, final String name, final SettlementOrRegion parent, final PlaceGroupId groupId) {
        super(id, name, groupId);
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
