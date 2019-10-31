package net.ramify.model.place.building;

import net.ramify.model.date.DateRange;
import net.ramify.model.place.type.BuildingHistory;

import javax.annotation.CheckForNull;

public class DefaultBuildingHistory implements BuildingHistory {

    private final DateRange built;
    private final DateRange closed;
    private final boolean demolished;

    public DefaultBuildingHistory(DateRange built, DateRange closed, boolean demolished) {
        this.built = built;
        this.closed = closed;
        this.demolished = demolished;
    }

    @CheckForNull
    @Override
    public DateRange opened() {
        return built;
    }

    @CheckForNull
    @Override
    public DateRange closed() {
        return closed;
    }

    @Override
    public boolean isDemolished() {
        return demolished;
    }

}
