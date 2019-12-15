package net.ramify.model.record.xml.record.wills;

import net.meerkat.collections.list.Lists;
import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "probateRecords")
public class XmlProbateRecords extends XmlRecords {

    @XmlElementRef
    private List<XmlProbateRecord> records;

    @Override
    public int numRecords() {
        return records == null ? 0 : records.size();
    }

    @Override
    public int numIndividuals() {
        return records == null ? 0 : records.stream().mapToInt(XmlProbateRecord::numIndividuals).sum();
    }

    @Nonnull
    @Override
    public Collection<? extends Record> build(final RecordSet recordSet, final RecordContext context) {
        return records == null ? Collections.emptyList() : Lists.eagerlyTransform(records, record -> record.build(context, recordSet));
    }

}
