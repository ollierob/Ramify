package net.ramify.model.record.type;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;

public interface ResidenceRecord extends LifeEventRecord {

    @Nonnull
    @Override
    default RecordProto.Record.Builder toProtoBuilder() {
        return LifeEventRecord.super.toProtoBuilder().setType(EventProto.RecordType.RESIDENCE);
    }

}
