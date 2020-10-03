package net.ramify.model.place.region.manor;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;
import java.util.Set;

public class Manor extends AbstractRegion {

    public static final PlaceHistory DEFAULT_HISTORY = new DefaultPlaceHistory(null, BeforeDate.approximatelyBefore(1925));
    static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Manor.class, Graveship.class);
    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Manor");

    private final Region parent;

    public Manor(
            final PlaceId id,
            final String name,
            final Region parent,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
    }

    private Region parent() {
        return parent;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

    @Nonnull
    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
