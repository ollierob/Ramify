package net.ramify.model.record.collection;

import net.ramify.model.Has;
import net.ramify.model.record.Record;

import javax.annotation.Nonnull;
import java.util.Set;

public interface HasRecords extends Has<Record> {

    @Nonnull
    Set<Record> records();

    @Override
    @Deprecated
    default Set<Record> values() {
        return this.records();
    }

}
