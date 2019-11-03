package net.ramify.model.place.building;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.BuildingHistory;
import net.ramify.model.place.type.SettlementOrRegion;

import java.util.Objects;

public class Monastery extends AbstractBuilding implements Institution {

    private final SettlementOrRegion parent;

    public Monastery(
            final PlaceId id,
            final String name,
            final Place parent,
            final PlaceGroupId groupId,
            final BuildingHistory history) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(SettlementOrRegion.class), groupId, history);
    }

    public Monastery(
            final PlaceId id,
            final String name,
            final SettlementOrRegion parent,
            final PlaceGroupId groupId,
            final BuildingHistory history) {
        super(id, name, groupId, history);
        this.parent = Objects.requireNonNull(parent, "parent");
    }

    @Override
    public SettlementOrRegion parent() {
        return parent;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.MONASTERY;
    }

}