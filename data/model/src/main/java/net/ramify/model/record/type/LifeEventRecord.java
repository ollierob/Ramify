package net.ramify.model.record.type;

import net.ramify.model.record.SingleFamilyRecord;

import javax.annotation.Nonnull;

public interface LifeEventRecord extends SingleFamilyRecord {

    @Override
    default <R> R handleWith(@Nonnull final RecordHandler<R> handler) {
        return handler.handle(this);
    }

}
