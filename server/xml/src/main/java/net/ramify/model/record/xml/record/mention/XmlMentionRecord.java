package net.ramify.model.record.xml.record.mention;

import com.google.common.base.MoreObjects;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlBetweenYears;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.death.GenericDeathEvent;
import net.ramify.model.event.type.misc.Flourished;
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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "mention")
public class XmlMentionRecord extends XmlPersonOnDateRecord {

    @XmlAttribute(name = "deceased")
    private Boolean deceased;

    @XmlElements({
            @XmlElement(name = "betweenYears", type = XmlBetweenYears.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "inYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE)
    })
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
    protected Set<Event> events(final PersonId personId, final DateRange date, final RecordContext context) {
        final var events = super.events(personId, date, context);
        if (this.deceased()) events.add(new GenericDeathEvent(personId, BeforeDate.strictlyBefore(date)));
        else events.add(new Flourished(personId, date));
        return events;
    }

}
