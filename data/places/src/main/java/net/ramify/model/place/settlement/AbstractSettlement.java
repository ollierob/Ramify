package net.ramify.model.place.settlement;

import net.ramify.model.place.AbstractPlace;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.type.Settlement;

import javax.annotation.Nonnull;

public abstract class AbstractSettlement extends AbstractPlace implements Settlement {

    private final Region region;

    protected AbstractSettlement(final PlaceId id, final String name, final Region region, final PlaceGroupId groupId) {
        super(id, name, groupId, null);
        this.region = region;
    }

    @Nonnull
    @Override
    public Region region() {
        return region;
    }

}
