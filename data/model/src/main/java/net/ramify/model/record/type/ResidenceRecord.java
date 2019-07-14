package net.ramify.model.record.type;

import net.ramify.model.place.HasPlace;
import net.ramify.model.record.SingleFamilyRecord;

import javax.annotation.Nonnull;

public interface ResidenceRecord extends SingleFamilyRecord, HasPlace {

    @Override
    default <R> R handleWith(@Nonnull RecordHandler<R> handler) {
        return handler.handle(this);
    }

}
