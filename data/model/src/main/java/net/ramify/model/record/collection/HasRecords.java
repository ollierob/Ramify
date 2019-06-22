package net.ramify.model.record.collection;

import net.ramify.model.record.Record;
import net.ramify.model.record.RecordId;
import net.ramify.utils.collections.IterableUtils;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasRecords {

    @Nonnull
    Set<Record> records();

    default boolean hasRecord(final RecordId id) {
        return IterableUtils.any(this.records(), r -> r.recordId().equals(id));
    }

}
