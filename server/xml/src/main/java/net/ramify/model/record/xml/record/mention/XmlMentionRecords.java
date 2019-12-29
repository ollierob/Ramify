package net.ramify.model.record.xml.record.mention;

import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.LifeEventRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.utils.collections.ListUtils;
import net.ramify.utils.objects.Functions;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "mentionRecords")
public class XmlMentionRecords extends XmlRecords {

    @XmlElementRef(namespace = XmlDateRange.NAMESPACE, required = false)
    private XmlDateRange date;

    @XmlElementRefs({
            @XmlElementRef(type = XmlMentionPersonRecord.class)
    })
    private List<XmlMentionRecord> records;

    @Override
    public int numRecords() {
        return records.size();
    }

    @Override
    public int numIndividuals() {
        return records.stream().mapToInt(XmlMentionRecord::numIndividuals).sum();
    }

    @Nonnull
    @Override
    public Collection<? extends LifeEventRecord> build(
            final RecordSet recordSet,
            final RecordContext context) {
        final var date = Functions.ifNonNull(this.date, d -> d.resolve(context.dateParser()), recordSet.date());
        return ListUtils.eagerlyTransform(records, record -> record.buildRecord(context.onDate(date), recordSet));
    }

}
