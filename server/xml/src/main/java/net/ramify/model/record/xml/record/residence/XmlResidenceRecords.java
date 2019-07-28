package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "residenceRecords")
public class XmlResidenceRecords extends XmlRecords {

    @XmlAttribute(name = "placeId", required = false)
    private String placeId;

    @XmlElements({
            @XmlElement(name = "inYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
    })
    private XmlDateRange date;

    @XmlElementRef
    private List<XmlResidenceRecord> records;

    @Override
    public int size() {
        return records == null ? 0 : records.size();
    }

    @Nonnull
    @Override
    public Collection<? extends LifeEventRecord> build(
            final RecordSet recordSet,
            final RecordContext context) {
        final var place = context.places().require(Functions.ifNonNull(this.placeId, PlaceId::new, recordSet.placeId()));
        final var date = Functions.ifNonNull(this.date, d -> d.resolve(context.dateParser()), recordSet.date());
        return ListUtils.eagerlyTransform(records, record -> record.build(place, date, context, recordSet));
    }

}
