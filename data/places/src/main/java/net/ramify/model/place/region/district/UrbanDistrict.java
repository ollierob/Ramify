package net.ramify.model.place.region.district;

import net.ramify.model.date.InYears;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.AbstractRegion;
import net.ramify.model.place.region.County;

public class UrbanDistrict extends AbstractRegion implements District {

    public static final PlaceHistory HISTORY = new DefaultPlaceHistory(new InYears(1894), new InYears(1974));

    private final County parent;

    public UrbanDistrict(final PlaceId id, final String name, final Place parent, final PlaceGroupId groupId, final PlaceHistory history) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(County.class), groupId, history);
    }

    public UrbanDistrict(final PlaceId id, final String name, final County parent, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, groupId, history);
        this.parent = parent;
    }

    @Override
    public County parent() {
        return parent;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.URBAN_DISTRICT;
    }

}
