package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;

public class GenericMentionRecord extends GenericLifeEventRecord {

    public GenericMentionRecord(RecordId recordId, RecordSet recordSet, Family family, DateRange date) {
        super(recordId, recordSet, family, date);
    }

    @Override
    protected EventProto.RecordType type() {
        return EventProto.RecordType.MENTION;
    }

}
