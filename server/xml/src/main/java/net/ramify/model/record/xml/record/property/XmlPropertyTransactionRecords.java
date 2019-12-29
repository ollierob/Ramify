package net.ramify.model.record.xml.record.property;

import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "propertyRecords")
public class XmlPropertyTransactionRecords extends XmlRecords {

    @XmlElementRef
    private List<XmlPropertyTransactionRecord> records;

    @Override
    public int numRecords() {
        return records == null ? 0 : records.size();
    }

    @Override
    public int numIndividuals() {
        return records == null
                ? 0
                : records.stream().mapToInt(XmlPropertyTransactionRecord::numIndividuals).sum();
    }

    @Nonnull
    @Override
    public Collection<? extends Record> build(final RecordSet recordSet, final RecordContext context) {
        if (records == null || records.isEmpty()) return Collections.emptyList();
        return ListUtils.eagerlyTransform(records, record -> record.toRecord(recordSet, context));
    }

}
