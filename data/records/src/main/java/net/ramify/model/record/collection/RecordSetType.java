package net.ramify.model.record.collection;

import net.ramify.model.event.proto.EventProto;

import javax.annotation.Nonnull;

public interface RecordSetType {

    @Nonnull
    String name();

    @Nonnull
    EventProto.RecordType toProto();

}
