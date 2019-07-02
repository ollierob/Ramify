package net.ramify.model.place.region;

import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.Nonnull;

public class Hundred extends AbstractRegion {

    private final County parent;

    public Hundred(final PlaceId id, final String name, final County parent) {
        super(id, name);
        this.parent = parent;
    }

    @Nonnull
    @Override
    public County parent() {
        return parent;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.HUNDRED;
    }
}
