package net.ramify.model.record.type;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.record.SingleFamilyRecord;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;

public interface BurialRecord extends PostDeathRecord, HasPlaceId {

    @Nonnull
    @Override
    default RecordProto.Record.Builder toProtoBuilder() {
        return PostDeathRecord.super.toProtoBuilder().setType(EventProto.RecordType.BURIAL);
    }

}
