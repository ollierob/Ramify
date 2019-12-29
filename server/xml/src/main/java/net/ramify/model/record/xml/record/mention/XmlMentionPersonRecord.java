package net.ramify.model.record.xml.record.mention;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.GenericMentionRecord;
import net.ramify.model.record.type.LifeEventRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "mention")
public class XmlMentionPersonRecord extends XmlPersonOnDateWithFamilyRecord implements XmlMentionRecord {

    @XmlAttribute(name = "deceased")
    private Boolean deceased;

    @XmlElementRef(namespace = XmlDateRange.NAMESPACE, required = false)
    private XmlDateRange date;

    protected boolean deceased() {
        return Boolean.TRUE.equals(deceased);
    }

    @Override
    public LifeEventRecord buildRecord(final RecordContext context, final RecordSet recordSet) {
        final var recordId = this.recordId();
        final var date = this.date(context);
        final var family = this.family(context.onDate(date));
        return new GenericMentionRecord(
                recordId,
                recordSet,
                family,
                date);
    }

    @CheckForNull
    DateRange date(final RecordContext context) {
        return date == null ? context.recordDate() : date.resolve(context.dateParser());
    }

    @Override
    protected MutablePersonEventSet events(final PersonId personId, final RecordContext context) {
        final var events = super.events(personId, context);
        final var date = context.recordDate();
        if (this.deceased()) {
            events.add(this.eventBuilder(BeforeDate.strictlyBefore(date)).toDeath(personId));
        } else {
            events.add(this.eventBuilder(date).toFlourished(personId));
        }
        return events;
    }

}
