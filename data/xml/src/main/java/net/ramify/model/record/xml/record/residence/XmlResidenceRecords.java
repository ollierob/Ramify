package net.ramify.model.record.xml.record.residence;

import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.type.ResidenceRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.XmlRecords;
import net.ramify.utils.collections.ListUtils;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "residenceRecords")
public class XmlResidenceRecords extends XmlRecords implements HasPlaceId {

    @XmlAttribute(name = "placeId", required = false)
    private String placeId;

    @XmlElements({
            @XmlElement(name = "inYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
    })
    private XmlDateRange date;

    @XmlElementRef
    private List<XmlResidenceRecord> records;

    @Nonnull
    @Override
    public Collection<? extends ResidenceRecord> build(final NameParser nameParser, final DateParser dateParser) {
        final var placeId = this.placeId();
        final var date = this.date.resolve(dateParser);
        return ListUtils.eagerlyTransform(records, record -> record.build(nameParser, placeId, date));
    }

    @Nonnull
    @Override
    public PlaceId placeId() {
        return new PlaceId(placeId);
    }

}
