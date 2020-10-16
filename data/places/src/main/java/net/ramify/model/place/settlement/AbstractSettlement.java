package net.ramify.model.place.settlement;

import net.ramify.model.place.AbstractPlace;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.type.Settlement;

public abstract class AbstractSettlement extends AbstractPlace implements Settlement {

    protected AbstractSettlement(
            final PlaceId id,
            final String name,
            final PlaceGroupId groupId,
            final PlaceHistory history) {
        super(id, name, groupId, history);
    }

}
