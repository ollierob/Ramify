package net.ramify.model.place.region.district;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.date.ExactDate;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.type.Settlement;

import javax.annotation.Nonnull;
import java.time.Month;
import java.util.Optional;
import java.util.Set;

public class MetropolitanBorough extends AbstractRegion implements District {

    private static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Region.class, Settlement.class);
    public static final PlaceHistory BOROUGH_HISTORY = new DefaultPlaceHistory(ExactDate.on(1974, Month.APRIL, 1), null);

    private final Region parent;
    private final String iso;

    public MetropolitanBorough(
            final PlaceId id,
            final String name,
            final Place parent,
            final PlaceGroupId groupId,
            final String iso,
            final PlaceHistory history) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class), groupId, iso, history);
    }

    public MetropolitanBorough(
            final PlaceId id,
            final String name,
            final Region parent,
            final PlaceGroupId groupId,
            final String iso,
            final PlaceHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
        this.iso = iso;
    }

    @Override
    public Region parent() {
        return parent;
    }

    @Nonnull
    @Override
    public Set<Class<? extends Place>> childTypes() {
        return CHILD_TYPES;
    }

    @Nonnull
    @Override
    public Optional<String> iso() {
        return Optional.ofNullable(iso);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.BOROUGH;
    }

}
