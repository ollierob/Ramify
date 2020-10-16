package net.ramify.model.place.region.district;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountrySubdivisionIso;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;

import javax.annotation.Nonnull;
import java.util.Optional;

public class Borough extends AbstractRegion implements District {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Borough");

    private final CountrySubdivisionIso iso;

    public Borough(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final CountrySubdivisionIso iso,
            final PlaceHistory history) {
        super(id, name, groupId, history);
        this.iso = iso;
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
