package net.ramify.model.record.xml;

import net.ramify.model.record.collection.RecordSetId;
import net.ramify.model.record.collection.Records;
import net.ramify.model.record.provider.RecordsProvider;

import javax.annotation.CheckForNull;
import javax.inject.Singleton;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@Singleton
class XmlRecordProvider implements RecordsProvider {

    @CheckForNull
    @Override
    public Records get(final RecordSetId key) {
        return null;
    }

}
