package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.settlement.City;

import javax.annotation.Nonnull;
import java.util.Set;

public class State extends AbstractRegion {

    static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(County.class, City.class);

    private final Country country;

    public State(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Country.class), groupId);
    }

    public State(final PlaceId id, final String name, final Country country, final PlaceGroupId groupId) {
        super(id, name, groupId);
        this.country = country;
    }

    @Nonnull
    @Override
    public Country parent() {
        return country;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.STATE;
    }

}
