package net.ramify.model.place.region.manor;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;

import javax.annotation.Nonnull;
import java.util.Set;

public class Graveship extends AbstractRegion {

    private final Manor parent;

    public Graveship(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Manor.class));
    }

    public Graveship(final PlaceId id, final String name, final Manor parent) {
        super(id, name);
        this.parent = parent;
    }

    @Override
    public Manor parent() {
        return parent;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return Manor.CHILD_TYPES;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.GRAVESHIP;
    }

}
