package net.ramify.model.place.region;

import net.ramify.model.place.AbstractPlace;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Region;

public abstract class AbstractRegion extends AbstractPlace implements Region {

    protected AbstractRegion(final PlaceId id, final String name, final PlaceGroupId groupId) {
        super(id, name, groupId);
    }

}
