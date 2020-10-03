package net.ramify.model.place.settlement;

import net.ramify.model.place.AbstractPlace;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.type.Settlement;
import net.ramify.model.place.type.SettlementOrRegion;

import javax.annotation.Nonnull;

public abstract class AbstractSettlement extends AbstractPlace implements Settlement {

    private final SettlementOrRegion region;

    protected AbstractSettlement(final PlaceId id, final String name, final SettlementOrRegion region, final PlaceGroupId groupId, final PlaceHistory history) {
        super(id, name, groupId, history);
        this.region = region;
    }

    @Nonnull
    private SettlementOrRegion parent() {
        return region;
    }

}
