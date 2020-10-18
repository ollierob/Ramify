package net.ramify.model.place.region;

import com.google.common.collect.ImmutableSet;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.settlement.City;
import net.ramify.model.place.settlement.Town;
import net.ramify.model.place.settlement.Village;

import java.util.Set;

public class Parish extends AbstractRegion {

    static final Set<Class<? extends Place>> CHILD_TYPES = ImmutableSet.of(Chapelry.class, Township.class, City.class, Town.class, Village.class, Church.class);
    private static final PlaceProto.PlaceType PLACE_TYPE = placeType("Parish");

    public Parish(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PLACE_TYPE;
    }

}
