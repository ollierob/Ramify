package net.ramify.model.place.region;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.Nonnull;

public class CountryRegion extends AbstractRegion {

    private final Country parent;

    public CountryRegion(PlaceId id, String name, PlaceGroupId groupId, PlaceHistory history, Country parent) {
        super(id, name, groupId, history);
        this.parent = parent;
    }

    @Override
    public Country parent() {
        return parent;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        throw new UnsupportedOperationException(); //TODO
    }

}
