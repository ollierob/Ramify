package net.ramify.model.record.xml.record.census.england;

import net.meerkat.collections.list.Lists;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.date.xml.XmlInMonth;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.EventId;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.type.BirthEvent;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.residence.CensusRecord;
import net.ramify.model.record.residence.uk.Register1939Record;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.model.record.xml.record.country.uk.XmlUkRecord;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import java.util.List;
import java.util.UUID;

@XmlRootElement(namespace = XmlUkRecord.NAMESPACE, name = "register1939")
public class XmlEngland1939RegisterRecord extends XmlEnglandCensusRecord {

    @XmlElements({
            @XmlElement(namespace = XmlUkRecord.NAMESPACE, name = "individual", type = XmlEngland1939RegisterIndividual.class),
            @XmlElement(namespace = XmlUkRecord.NAMESPACE, name = "closed", type = XmlEngland1939RegisterClosedEntry.class),
    })
    private List<RegisterIndividual> individuals;

    @Override
    protected CensusRecord build(final RecordContext context, final Place censusPlace, final RecordSet recordSet) {
        return new Register1939Record(
                this.recordId(),
                recordSet,
                this.place(context, censusPlace),
                this.entries(context),
                context.uniqueEventMerger());
    }

    private List<Register1939Record.Register1939Entry> entries(final RecordContext context) {
        return Lists.eagerlyTransform(individuals, i -> i.toEntry(context));
    }

    @Override
    public int numIndividuals() {
        return individuals == null ? 0 : individuals.size();
    }

    @XmlTransient
    private interface RegisterIndividual {

        Register1939Record.Register1939Entry toEntry(RecordContext context);

    }

    @XmlType(namespace = XmlUkRecord.NAMESPACE)
    public static class XmlEngland1939RegisterIndividual extends XmlPersonRecord implements RegisterIndividual {

        @XmlAttribute(name = "birthEventId")
        private String birthEventId = UUID.randomUUID().toString();

        @XmlAttribute(name = "residenceEventId")
        private String residenceEventId = UUID.randomUUID().toString();

        @XmlElements({
                @XmlElement(name = "bornOn", namespace = XmlDateRange.NAMESPACE, type = XmlExactDate.class),
                @XmlElement(name = "bornIn", namespace = XmlDateRange.NAMESPACE, type = XmlInMonth.class)
        })
        private XmlDateRange birthDate;

        @Override
        protected MutablePersonEventSet events(final PersonId personId, final RecordContext context) {
            final var events = super.events(personId, context);
            events.add(this.birth(personId, this.birthDate(context)));
            return events;
        }

        protected DateRange birthDate(final RecordContext context) {
            return birthDate.resolve(context.dateParser());
        }

        protected BirthEvent birth(final PersonId personId, final DateRange birthDate) {
            return EventBuilder.builderWithRandomId(birthDate).toBirth(personId);
        }

        @Override
        public Register1939Record.Register1939Entry toEntry(final RecordContext context) {
            return new Register1939Record.Register1939Entry(
                    this.personId(),
                    this.name(context.nameParser()),
                    this.gender(),
                    this.birthDate(context),
                    this.occupation(),
                    new EventId(birthEventId),
                    new EventId(residenceEventId));
        }

    }

    @XmlType(namespace = XmlUkRecord.NAMESPACE)
    public static class XmlEngland1939RegisterClosedEntry extends XmlRecord implements RegisterIndividual {

        @XmlAttribute(name = "personId")
        private String personId = UUID.randomUUID().toString();

        @Override
        public Register1939Record.Register1939Entry toEntry(RecordContext context) {
            return new Register1939Record.Register1939Entry(new PersonId(personId));
        }

        @Override
        public int numIndividuals() {
            return 1;
        }

    }

}
