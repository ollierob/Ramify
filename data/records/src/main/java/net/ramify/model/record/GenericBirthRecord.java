package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetType;
import net.ramify.model.record.type.BirthRecord;

public class GenericBirthRecord extends DateFamilyPlaceRecord implements BirthRecord {

    public GenericBirthRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final Family family,
            final PlaceId birthPlace) {
        super(id, recordSet, date, family, birthPlace);
    }

    @Override
    public RecordSetType type() {
        return RecordSetTypes.BIRTH;
    }

}
