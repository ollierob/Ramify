package net.ramify.model.record.civil;

import net.ramify.model.date.DateRange;
import net.ramify.model.record.DateRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.SingleFamilyRecord;
import net.ramify.model.record.collection.RecordSet;

public abstract class AbstractCivilRecord extends DateRecord implements SingleFamilyRecord {

    protected AbstractCivilRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange eventDate) {
        super(id, recordSet, eventDate);
    }

}
