package net.ramify.model.record.provider;

import net.ramify.model.record.Record;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.util.provider.Provider;

import javax.annotation.CheckForNull;

public interface RecordProvider extends Provider<RecordId, Record> {

    @CheckForNull
    default RecordSet getRecordSet(final RecordId id) {
        return this.maybeGet(id).map(Record::recordSet).orElse(null);
    }

    @CheckForNull
    default RecordSetId getRecordSetId(final RecordId id) {
        return this.maybeGet(id).map(Record::recordSetId).orElse(null);
    }

}
