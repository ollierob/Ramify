package net.ramify.model.record.xml.record.death;

import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "memorialInscription")
public class XmlMemorialInscription extends XmlRecords {

    @XmlElement(name = "text", namespace = XmlRecord.NAMESPACE, required = true)
    private String text;

    @XmlElementRef
    private List<XmlDeathRecord> deaths;

    @Override
    public int numRecords() {
        return deaths == null ? 0 : deaths.size();
    }

    @Override
    public int numIndividuals() {
        return deaths == null ? 0 : deaths.stream().mapToInt(XmlDeathRecord::numIndividuals).sum();
    }

    @Nonnull
    @Override
    public Collection<? extends Record> build(final RecordSet recordSet, final RecordContext context) {
        return ListUtils.eagerlyTransform(deaths, record -> record.build(recordSet.covers(), context, recordSet));
    }

}
