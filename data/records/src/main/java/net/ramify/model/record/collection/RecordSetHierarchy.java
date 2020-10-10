package net.ramify.model.record.collection;

import net.meerkat.functions.consumers.Consumers;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.record.proto.RecordProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface RecordSetHierarchy extends BuildsProto<RecordProto.RecordSetHierarchy> {

    @Nonnull
    RecordSetRelatives relatives();

    @CheckForNull
    RecordSetHierarchy parent();

    default RecordProto.RecordSetHierarchy.Builder toProtoBuilder() {
        final var builder = RecordProto.RecordSetHierarchy.newBuilder()
                .setSelf(this.relatives().toProto());
        Consumers.ifNonNull(this.parent(), parent -> builder.setParent(parent.toProtoBuilder()));
        return builder;
    }

    @Nonnull
    @Override
    default RecordProto.RecordSetHierarchy toProto() {
        return this.toProtoBuilder().build();
    }

}
