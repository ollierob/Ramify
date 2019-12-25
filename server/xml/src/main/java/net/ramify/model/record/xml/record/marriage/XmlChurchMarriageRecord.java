package net.ramify.model.record.xml.record.marriage;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.family.Family;
import net.ramify.model.family.UnionFamily;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.church.ChurchMarriageRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.MarriageRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.mention.XmlWitnessPerson;
import net.ramify.model.relationship.type.Married;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.UUID;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "churchMarriage")
public class XmlChurchMarriageRecord extends XmlMarriageRecord {

    @XmlAttribute(name = "eventId")
    private String eventId = UUID.randomUUID().toString();

    @XmlElementRef(namespace = XmlDateRange.NAMESPACE)
    private XmlDateRange date;

    @XmlElementRef
    private List<XmlMarriageSpouse> spouses;

    @XmlElementRef
    private List<XmlWitnessPerson> witnesses;

    @Override
    public int numIndividuals() {
        return spouses == null ? 0 : spouses.stream().mapToInt(XmlPersonOnDateWithFamilyRecord::numIndividuals).sum();
    }

    @Override
    public MarriageRecord build(final RecordContext context, final RecordSet recordSet, final PlaceId marriagePlaceId) {
        if (spouses == null || spouses.size() != 2) return null;
        final var date = this.date(context);
        final var family = this.family(context.onDate(date));
        return new ChurchMarriageRecord(
                this.recordId(),
                recordSet,
                date,
                family,
                marriagePlaceId);
    }

    protected Family family(final RecordContext context) {
        final var eventId = this.eventId();
        final var first = spouses.get(0).family(context, eventId);
        final var second = spouses.get(1).family(context, eventId);
        return new UnionFamily(first, second, new Married(first.root(), second.root()));
    }

    private EventId eventId() {
        return new EventId(eventId);
    }

    protected DateRange date(final RecordContext context) {
        return date.resolve(context.dateParser());
    }

}
