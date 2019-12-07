package net.ramify.model.record.type;

import net.ramify.model.event.proto.EventProto;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.record.SingleFamilyRecord;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.Nonnull;

public interface MarriageRecord extends SingleFamilyRecord, HasPlaceId {

    @Override
    default <R> R handleWith(@Nonnull final RecordHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default RecordProto.Record.Builder toProtoBuilder() {
        return SingleFamilyRecord.super.toProtoBuilder().setType(EventProto.RecordType.MARRIAGE);
    }

}
