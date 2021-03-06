package net.ramify.model.record.type;

import net.ramify.model.place.HasPlaceId;
import net.ramify.model.record.SingleFamilyRecord;

import javax.annotation.Nonnull;

public interface BirthRecord extends SingleFamilyRecord, HasPlaceId {

    @Override
    default <R> R handleWith(@Nonnull RecordHandler<R> handler) {
        return handler.handle(this);
    }

}
