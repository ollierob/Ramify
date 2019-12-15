package net.ramify.model.record.xml.record.wills;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.will.ProbateEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.will.probate.ProbateRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "probate")
public class XmlProbateRecord extends XmlPersonOnDateWithFamilyRecord {

    @XmlElements({
            @XmlElement(name = "inYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "onDate", type = XmlExactDate.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange date;

    @Nonnull
    public ProbateRecord build(final RecordContext context, final RecordSet recordSet) {
        final var date = this.date(context);
        return new ProbateRecord(
                this.recordId(),
                recordSet,
                date,
                this.family(context, date));
    }

    @Override
    protected Set<Event> events(final PersonId personId, final DateRange date, final RecordContext context) {
        final var events = super.events(personId, date, context);
        events.add(this.probate(personId, date));
        return events;
    }

    protected ProbateEvent probate(final PersonId personId, final DateRange date) {
        return this.eventBuilder(date).toProbate(personId);
    }

    protected DateRange date(final RecordContext context) {
        if (this.date != null) return date.resolve(context.dateParser());
        return context.recordDate();
    }

}
