package net.ramify.model.record.church;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.MarriageRecord;

public class ChurchMarriageRecord extends AbstractChurchRecord implements MarriageRecord {

    public ChurchMarriageRecord(RecordId id, RecordSet recordSet, DateRange date, Family family, PlaceId churchId) {
        super(id, recordSet, date, family, churchId);
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.MARRIAGE;
    }

}
