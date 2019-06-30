package net.ramify.model.record.church;

import net.ramify.model.date.ExactDate;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.AbstractRecord;
import net.ramify.model.record.RecordId;

public abstract class AbstractChurchRecord extends AbstractRecord {

    private final PlaceId church;

    protected AbstractChurchRecord(
            final RecordId id,
            final ExactDate date,
            final PlaceId church) {
        super(id, date);
        this.church = church;
    }

    PlaceId church() {
        return church;
    }

}