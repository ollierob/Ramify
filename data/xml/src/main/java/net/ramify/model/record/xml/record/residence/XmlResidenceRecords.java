package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.ResidenceRecord;
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
        return records.size();
    }

    @Nonnull
    @Override
    public Collection<? extends ResidenceRecord> build(
            final RecordSet recordSet,
            final NameParser nameParser,
            final DateParser dateParser) {
        final var placeId = Functions.ifNonNull(this.placeId, PlaceId::new, recordSet.placeId());
        final var date = Functions.ifNonNull(this.date, d -> d.resolve(dateParser), recordSet.date());
        return ListUtils.eagerlyTransform(records, record -> record.build(nameParser, placeId, date));
    }

}
