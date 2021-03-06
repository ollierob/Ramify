package net.ramify.model.record.church;

import net.ramify.model.date.ExactDate;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.RecordSetTypes;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetType;
import net.ramify.model.record.type.BaptismRecord;

public class ChurchBaptismRecord extends AbstractChurchRecord implements BaptismRecord {

    public ChurchBaptismRecord(
            final RecordId id,
            final RecordSet recordSet,
            final ExactDate date,
            final PlaceId church,
            final Family family) {
        super(id, recordSet, date, family, church);
    }

    @Override
    public RecordSetType type() {
        return RecordSetTypes.BAPTISM;
    }

}
