package net.ramify.model.record.church;

import net.ramify.model.date.ExactDate;
import net.ramify.model.event.proto.EventProto;
import net.ramify.model.family.Family;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.RecordId;

import javax.annotation.Nonnull;
import java.util.Set;

public class ChurchBaptismRecord extends AbstractChurchRecord {

    public ChurchBaptismRecord(
            final RecordId id,
            final ExactDate date,
            final PlaceId church) {
        super(id, date, church);
    }

    @Nonnull
    @Override
    public Set<? extends Family> families() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    protected EventProto.RecordType protoType() {
        return EventProto.RecordType.BAPTISM;
    }
}
