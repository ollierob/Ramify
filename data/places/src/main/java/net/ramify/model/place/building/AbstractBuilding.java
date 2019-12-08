package net.ramify.model.place.building;

import net.ramify.model.place.AbstractPlace;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Building;

import javax.annotation.CheckForNull;

public abstract class AbstractBuilding extends AbstractPlace implements Building {

    private final BuildingHistory history;

    protected AbstractBuilding(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final BuildingHistory history) {
        super(id, name, groupId, history);
        this.history = history;
    }

    @CheckForNull
    @Override
    public BuildingHistory history() {
        return history;
    }

    protected static PlaceProto.PlaceType.Builder placeTypeBuilder(final String name) {
        return PlaceProto.PlaceType.newBuilder()
                .setName(name)
                .setCanPrefix(false)
                .setCanSuffix(true)
                .setIsBuilding(true);
    }

    protected static PlaceProto.PlaceType placeType(final String name) {
        return placeTypeBuilder(name).build();
    }

}
