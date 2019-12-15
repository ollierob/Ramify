package net.ramify.model.record.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.DateFamilyPlaceRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;

public abstract class AbstractChurchRecord extends DateFamilyPlaceRecord {

    protected AbstractChurchRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final Family family,
            final PlaceId churchId) {
        super(id, recordSet, date, family, churchId);
    }

}
