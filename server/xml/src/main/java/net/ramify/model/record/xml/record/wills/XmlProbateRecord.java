package net.ramify.model.record.xml.record.wills;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.type.will.ProbateEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.will.probate.ProbateRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateWithFamilyRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "probate")
public class XmlProbateRecord extends XmlPersonOnDateWithFamilyRecord {

    @XmlElementRef(namespace = XmlDateRange.NAMESPACE)
    private XmlDateRange date;

    @Nonnull
    public ProbateRecord build(final RecordContext context, final RecordSet recordSet) {
        final var date = this.date(context);
        return new ProbateRecord(
                this.recordId(),
                recordSet,
                date,
                this.family(context.onDate(date)));
    }

    @Override
    protected MutablePersonEventSet events(final PersonId personId, final RecordContext context) {
        final var events = super.events(personId, context);
        events.add(this.probate(personId, context.recordDate()));
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
