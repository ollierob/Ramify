package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.BirthRecord;

public class GenericBirthRecord extends DatePlaceFamilyRecord implements BirthRecord {

    public GenericBirthRecord(
            final RecordId id,
            final RecordSet recordSet,
            final DateRange date,
            final PlaceId birthPlace,
            final Family family) {
        super(id, recordSet, date, birthPlace, family);
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.BIRTH;
    }

}
