package net.ramify.model.place.settlement;

import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.type.Region;

import javax.annotation.Nonnull;
import java.util.Optional;

public class City extends AbstractSettlement {

    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("City");

    private final CountrySubdivisionIso iso;

    public City(
            final PlaceId id,
            final String name,
            final Region region,
            final PlaceGroupId groupId,
            final PlaceHistory history,
            final CountrySubdivisionIso iso) {
        super(id, name, region, groupId, history);
        this.iso = iso;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

    @Nonnull
    @Override
    public Optional<CountrySubdivisionIso> iso() {
        return Optional.ofNullable(iso);
    }

}
