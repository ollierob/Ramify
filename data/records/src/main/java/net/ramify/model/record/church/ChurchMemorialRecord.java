package net.ramify.model.record.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.DateFamilyPlaceRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.RecordSetTypes;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetType;
import net.ramify.model.record.type.DeathRecord;

public class ChurchMemorialRecord extends DateFamilyPlaceRecord implements DeathRecord {

    public ChurchMemorialRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final PlaceId deathPlace,
            final Family family) {
        super(id, recordSet, date, family, deathPlace);
    }

    @Override
    public RecordSetType type() {
        return RecordSetTypes.MEMORIAL;
    }

}
