package net.ramify.model.record.xml.record.marriage;

import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventId;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.type.marriage.MarriageEvent;
import net.ramify.model.family.Family;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static net.ramify.utils.StringUtils.isBlank;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "marriageSpouse")
public class XmlMarriageSpouse extends XmlPersonOnDateWithFamilyRecord {

    @XmlAttribute(name = "signature")
    private XmlSignature signature;

    protected Family family(final RecordContext context, final EventId marriageEventId) {
        return super.family(context, context.recordDate(), id -> this.events(id, context, marriageEventId));
    }

    protected MarriageEvent marriage(final EventId eventId, final PersonId personId, final DateRange date) {
        return this.eventBuilder(eventId, date).toMarriage(personId);
    }

    protected MutablePersonEventSet events(final PersonId personId, final RecordContext context, final EventId marriageEventId) {
        final var events = super.events(personId, context.recordDate(), context);
        events.add(this.marriage(marriageEventId, personId, context.recordDate()));
        return events;
    }

    @CheckForNull
    @Override
    protected String notes() {
        final var notes = super.notes();
        if (!isBlank(notes)) return notes;
        if (signature == XmlSignature.SIGNATURE) return "Signed name";
        if (signature == XmlSignature.MARK) return "Marked name";
        return null;
    }

    @SuppressWarnings("ValidExternallyBoundObject")
    @XmlEnum
    @XmlType(namespace = XmlRecord.NAMESPACE)
    enum XmlSignature {

        @XmlEnumValue("signature")
        SIGNATURE,

        @XmlEnumValue("mark")
        MARK;

    }

}
