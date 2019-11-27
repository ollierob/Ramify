package net.ramify.model.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.MarriageRecord;

public class GenericMarriageRecord extends DatePlaceFamilyRecord implements MarriageRecord {

    protected GenericMarriageRecord(RecordId id, RecordSet recordSet, DateRange date, PlaceId place, Family family) {
        super(id, recordSet, date, place, family);
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.MARRIAGE;
    }

}