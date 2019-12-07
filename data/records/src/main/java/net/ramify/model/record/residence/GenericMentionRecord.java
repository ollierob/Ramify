package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.family.Family;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.MentionRecord;

public class GenericMentionRecord extends GenericLifeEventRecord implements MentionRecord {

    public GenericMentionRecord(RecordId recordId, RecordSet recordSet, Family family, DateRange date) {
        super(recordId, recordSet, family, date);
    }

}
