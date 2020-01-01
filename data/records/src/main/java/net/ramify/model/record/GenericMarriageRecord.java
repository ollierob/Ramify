package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetType;
import net.ramify.model.record.type.MarriageRecord;

import javax.annotation.Nonnull;

public class GenericMarriageRecord extends DateFamilyPlaceRecord implements MarriageRecord {

    public GenericMarriageRecord(final RecordId id, RecordSet recordSet, final DateRange date, final PlaceId place, final Family family) {
        super(id, recordSet, date, family, place);
    }

    @Nonnull
    @Override
    public RecordSetType type() {
        return RecordSetTypes.MARRIAGE;
    }

}
