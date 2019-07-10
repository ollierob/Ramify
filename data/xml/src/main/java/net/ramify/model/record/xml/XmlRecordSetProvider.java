package net.ramify.model.record.xml;

import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.provider.RecordSetProvider;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Map;

@XmlTransient
class XmlRecordSetProvider implements RecordSetProvider {

    private final Map<RecordSetId, RecordSet> recordSets;

    XmlRecordSetProvider(final Map<RecordSetId, RecordSet> recordSets) {
        this.recordSets = recordSets;
    }

    @CheckForNull
    @Override
    public RecordSet get(final RecordSetId key) {
        return recordSets.get(key);
    }

}
