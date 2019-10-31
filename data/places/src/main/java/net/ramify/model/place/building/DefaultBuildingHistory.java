package net.ramify.model.place.building;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.type.BuildingHistory;

public class DefaultBuildingHistory extends DefaultPlaceHistory implements BuildingHistory {

    private final boolean demolished;

    public DefaultBuildingHistory(DateRange built, DateRange closed, boolean demolished) {
        super(built, closed);
        this.demolished = demolished;
    }

    @Override
    public boolean isDemolished() {
        return demolished;
    }

}
