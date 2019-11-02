package net.ramify.model.record.xml.record.death;

import com.google.common.collect.Lists;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.DeathRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "memorialInscriptions")
public class XmlMemorialInscriptions extends XmlRecords {

    @XmlElementRef
    private List<XmlMemorialInscription> inscriptions;

    @Override
    public int numRecords() {
        return inscriptions == null ? 0 : inscriptions.size();
    }

    @Override
    public int numIndividuals() {
        return inscriptions == null ? 0 : inscriptions.stream().mapToInt(XmlMemorialInscription::numIndividuals).sum();
    }

    @Nonnull
    @Override
    public Collection<DeathRecord> build(final RecordSet recordSet, final RecordContext context) {
        if (inscriptions == null) return Collections.emptySet();
        final var records = Lists.<DeathRecord>newArrayList();
        inscriptions.forEach(inscription -> records.addAll(inscription.build(recordSet.covers(), context, recordSet)));
        return records;
    }

}
