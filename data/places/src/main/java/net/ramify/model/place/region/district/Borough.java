package net.ramify.model.place.region.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;
import java.util.Optional;

public class Borough extends AbstractRegion implements District {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Borough");

    private final Region parent;
    private final CountrySubdivisionIso iso;

    public Borough(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId, final CountrySubdivisionIso iso, final PlaceHistory history) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(Region.class), groupId, iso, history);
    }

    public Borough(final PlaceId id, final String name, final Region parent, final PlaceGroupId groupId, final CountrySubdivisionIso iso, final PlaceHistory history) {
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
    public Optional<CountrySubdivisionIso> iso() {
        return Optional.ofNullable(iso);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
