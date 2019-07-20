package net.ramify.model.record.church;

import net.ramify.model.date.ExactDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.type.BaptismRecord;

import javax.annotation.Nonnull;

public class ChurchBaptismRecord extends AbstractChurchRecord implements BaptismRecord {

    private final Family family;

    public ChurchBaptismRecord(
            final RecordId id,
            final RecordSetId recordSetId,
            final ExactDate date,
            final PlaceId church,
            final Family family) {
        super(id, recordSetId, date, church);
        this.family = family;
    }

    @Nonnull
    @Override
    public Family family() {
        return family;
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.BAPTISM;
    }

}
