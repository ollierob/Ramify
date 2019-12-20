package net.ramify.model.record.provider;

import net.ramify.model.record.Record;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.util.provider.Provider;
import net.ramify.utils.objects.Functions;

public interface RecordProvider extends Provider<RecordId, Record> {

    default RecordSet getRecordSet(final RecordId id) {
        return Functions.ifNonNull(this.get(id), Record::recordSet);
    }

}
