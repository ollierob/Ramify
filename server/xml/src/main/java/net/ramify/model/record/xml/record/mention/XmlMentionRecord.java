package net.ramify.model.record.xml.record.mention;

import com.google.common.base.MoreObjects;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.GenericMentionRecord;
import net.ramify.model.record.type.LifeEventRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "mention")
public class XmlMentionRecord extends XmlPersonOnDateRecord {

    @XmlAttribute(name = "deceased")
    private Boolean deceased;

    @XmlElementRef(namespace = XmlDateRange.NAMESPACE, required = false)
    private XmlDateRange date;

    protected boolean deceased() {
        return Boolean.TRUE.equals(deceased);
    }

    public LifeEventRecord buildRecord(final DateRange parentDate, final RecordContext context, final RecordSet recordSet) {
        final var recordId = this.recordId();
        final var date = MoreObjects.firstNonNull(this.date(context.dateParser()), parentDate);
        final var family = new SinglePersonFamily(this.person(date, context));
        return new GenericMentionRecord(
                recordId,
                recordSet,
                family,
                date);
    }

    @CheckForNull
    DateRange date(final DateParser dates) {
        return date == null ? null : date.resolve(dates);
    }

    @Override
    protected MutablePersonEventSet events(final PersonId personId, final DateRange date, final RecordContext context) {
        final var events = super.events(personId, date, context);
        if (this.deceased()) {
            events.add(this.eventBuilder(BeforeDate.strictlyBefore(date)).toDeath(personId));
        } else {
            events.add(this.eventBuilder(date).toFlourished(personId));
        }
        return events;
    }

}
