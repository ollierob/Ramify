package net.ramify.model.record.xml.record.marriage;

import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "marriageRecords")
public class XmlMarriageRecords extends XmlRecords {

    private List<XmlMarriageRecord> marriageRecords;

    @Override
    public int numRecords() {
        return marriageRecords == null ? 0 : marriageRecords.size();
    }

    @Override
    public int numIndividuals() {
        return marriageRecords == null ? 0 : marriageRecords.stream().mapToInt(XmlMarriageRecord::numIndividuals).sum();
    }

    @Nonnull
    @Override
    public Collection<? extends Record> build(final RecordSet recordSet, final RecordContext context) {
        if (marriageRecords == null) return Collections.emptyList();
        return ListUtils.eagerlyTransform(marriageRecords, record -> record.toRecord(context));
    }

}
