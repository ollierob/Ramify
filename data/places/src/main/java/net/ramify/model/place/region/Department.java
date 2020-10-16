package net.ramify.model.place.region;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.Nonnull;

public class Department extends AbstractRegion {

    public Department(final PlaceId id, final String name, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, groupId, history);
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        throw new UnsupportedOperationException(); //TODO
    }

}
