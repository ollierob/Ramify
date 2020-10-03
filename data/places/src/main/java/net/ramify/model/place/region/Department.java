package net.ramify.model.place.region;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;

public class Department extends AbstractRegion {

    private final Region parent;

    public Department(PlaceId id, String name, PlaceGroupId groupId, PlaceHistory history, Region parent) {
        super(id, name, groupId, history);
        this.parent = parent;
    }

    private Region parent() {
        return parent;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        throw new UnsupportedOperationException(); //TODO
    }

}
