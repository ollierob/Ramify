package net.ramify.model.record.xml.record.birth;

import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.BirthRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "birthRecords")
public class XmlBirthRecords extends XmlRecords {

    @XmlElementRef
    private List<XmlBirthRecord> records;

    @Override
    public int numRecords() {
        return records == null ? 0 : records.size();
    }

    @Override
    public int numIndividuals() {
        return records == null ? 0 : records.stream().mapToInt(XmlBirthRecord::numIndividuals).sum();
    }

    @Nonnull
    @Override
    public Collection<? extends BirthRecord> build(
            final RecordSet recordSet,
            final RecordContext context) {
        return ListUtils.eagerlyTransform(records, record -> record.build(recordSet.covers(), context, recordSet));
    }

}
