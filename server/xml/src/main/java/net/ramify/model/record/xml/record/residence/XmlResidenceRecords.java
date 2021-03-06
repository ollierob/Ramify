package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.LifeEventRecord;
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

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "residenceRecords")
public class XmlResidenceRecords extends XmlRecords {

    @XmlAttribute(name = "placeId")
    private String placeId;

    @XmlElementRef(namespace = XmlDateRange.NAMESPACE, required = false)
    private XmlDateRange date;

    @XmlElementRef
    private List<XmlResidenceRecord> records;

    @Override
    public int numRecords() {
        return records == null ? 0 : records.size();
    }

    @Override
    public int numIndividuals() {
        return this.numRecords(); //TODO relationships?
    }

    @Nonnull
    @Override
    public Collection<? extends LifeEventRecord> build(
            final RecordSet recordSet,
            final RecordContext context) {
        if (records == null) return Collections.emptyList();
        final var place = context.places().require(Functions.ifNonNull(this.placeId, PlaceId::new, recordSet.placeId()));
        final var date = this.date(recordSet, context);
        return ListUtils.eagerlyTransform(records, record -> record.build(place, context.onDate(date), recordSet));
    }

    private DateRange date(final RecordSet recordSet, final RecordContext context) {
        if (date != null) return date.resolve(context.dateParser());

        return Functions.ifNonNull(this.date, d -> d.resolve(context.dateParser()), context.recordDate());
    }

}
