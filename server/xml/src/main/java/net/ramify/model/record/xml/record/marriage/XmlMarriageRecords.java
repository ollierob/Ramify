package net.ramify.model.record.xml.record.marriage;

import net.ramify.model.place.PlaceId;
import net.ramify.model.record.Record;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.utils.collections.ListUtils;
import net.ramify.utils.objects.Functions;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "marriageRecords")
public class XmlMarriageRecords extends XmlRecords {

    @XmlElementRef
    private List<XmlMarriageRecord> marriageRecords;

    @XmlAttribute(name = "marriagePlace")
    private String marriagePlaceId;

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
        final var marriagePlace = this.marriagePlace(recordSet);
        return ListUtils.eagerlyTransform(marriageRecords, record -> record.build(context, recordSet, marriagePlace));
    }

    protected PlaceId marriagePlace(final RecordSet recordSet) {
        return Functions.ifNonNull(marriagePlaceId, PlaceId::new, recordSet.createdBy().placeId());
    }

}
