package net.ramify.model.place.building;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.BuildingHistory;

import java.util.Objects;

public class Graveyard extends AbstractBuilding implements Institution {

    private final Place parent;

    public Graveyard(
            final PlaceId id,
            final String name,
            final Place parent,
            final PlaceGroupId groupId,
            final BuildingHistory history) {
        super(id, name, groupId, history);
        this.parent = Objects.requireNonNull(parent, "parent");
    }

    @Override
    public Place parent() {
        return parent;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.GRAVEYARD;
    }

}
