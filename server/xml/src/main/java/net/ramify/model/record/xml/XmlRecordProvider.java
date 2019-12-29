package net.ramify.model.record.xml;

import com.google.common.collect.Maps;
import net.ramify.model.record.Record;
import net.ramify.model.record.RecordId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.provider.RecordProvider;
import net.ramify.model.record.provider.RecordsProvider;

import javax.annotation.CheckForNull;
import javax.inject.Inject;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Map;

@XmlTransient
class XmlRecordProvider implements RecordProvider {

    private final RecordsProvider recordsProvider;
    private final Map<RecordId, RecordSet> recordSets = Maps.newConcurrentMap();

    @Inject
    XmlRecordProvider(final RecordsProvider recordsProvider) {
        this.recordsProvider = recordsProvider;
    }

    @CheckForNull
    @Override
    public RecordSet getRecordSet(final RecordId id) {
        synchronized (recordSets) {
            if (recordSets.isEmpty()) this.indexRecordSets();
        }
        return recordSets.get(id);
    }

    private void indexRecordSets() {
        recordsProvider.all()
                .stream()
                .flatMap(Records::records)
                .forEach(record -> recordSets.put(record.recordId(), record.recordSet()));
    }

    @CheckForNull
    @Override
    public Record get(final RecordId id) {
        final var recordSet = this.getRecordSet(id);
        if (recordSet == null) return null;
        final var records = recordsProvider.get(recordSet.recordSetId());
        if (records == null) return null;
        return records.record(id).orElse(null);
    }

}
