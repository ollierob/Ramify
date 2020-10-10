package net.ramify.model.record.collection;

import net.meerkat.collections.Collections;
import net.ramify.model.record.Record;
import net.ramify.model.record.RecordId;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasRecords {

    @Nonnull
    Set<Record> records();

    default boolean hasRecord(@Nonnull final RecordId id) {
        return Collections.any(this.records(), r -> r.recordId().equals(id));
    }

}
