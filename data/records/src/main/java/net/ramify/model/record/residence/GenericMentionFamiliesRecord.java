package net.ramify.model.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.record.DatedRecord;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.MentionRecord;

import javax.annotation.Nonnull;
import java.util.Set;

public class GenericMentionFamiliesRecord extends DatedRecord implements MentionRecord {

    private final Set<Family> families;

    public GenericMentionFamiliesRecord(
            final RecordId recordId,
            final RecordSet recordSet,
            final Set<Family> families,
            final DateRange date) {
        super(recordId, recordSet, date);
        this.families = families;
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.MENTION;
    }

    @Nonnull
    @Override
    public Set<? extends Family> families() {
        return families;
    }

}
