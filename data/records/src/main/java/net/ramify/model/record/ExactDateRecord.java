package net.ramify.model.record;

import net.ramify.model.date.ExactDate;
import net.ramify.model.record.collection.RecordSet;

public abstract class ExactDateRecord extends DatedRecord {

    private final ExactDate date;

    protected ExactDateRecord(
            final RecordId id,
            final RecordSet recordSet,
            final ExactDate date) {
        super(id, recordSet, date);
        this.date = date;
    }

    @Override
    public ExactDate date() {
        return date;
    }

}
