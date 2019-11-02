package net.ramify.model.record.xml.record.death;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.burial.Burial;
import net.ramify.model.event.type.burial.ChurchBurial;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.event.type.residence.GenericResidence;
import net.ramify.model.event.type.residence.Residence;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.xml.XmlRelationship;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.church.ChurchBurialRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.BurialRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.utils.objects.Functions;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Month;
import java.util.List;
import java.util.Set;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "churchBurial")
public class XmlBurialRecord extends XmlPersonOnDateRecord {

    @XmlAttribute(name = "residence", required = false)
    private String residence;

    @XmlAttribute(name = "year")
    private int year;

    @XmlAttribute(name = "month")
    private int month;

    @XmlAttribute(name = "day")
    private int dayOfMonth;

    @XmlElement(name = "deathDate", required = false, namespace = XmlDateRange.NAMESPACE)
    private XmlExactDate deathDate;

    @XmlElementRef
    private List<XmlRelationship> relationships;

    public BurialRecord build(final PlaceId placeId, final RecordContext context, final RecordSet recordSet) {
        final var burialDate = ExactDate.on(year, Month.of(month), dayOfMonth);
        return new ChurchBurialRecord(
                this.recordId(),
                recordSet,
                burialDate,
                placeId,
                this.family(burialDate, context));
    }

    Family family(final ExactDate burialDate, final RecordContext context) {
        final var person = this.person(context.nameParser(), burialDate, context.places());
        final var builder = new FamilyBuilder().addPerson(person);
        if (relationships != null) relationships.forEach(relationship -> relationship.addRelationship(person, builder, context.nameParser(), burialDate));
        return builder.build();
    }

    Person person(final NameParser nameParser, final ExactDate burialDate, final PlaceProvider places) {
        final var personId = this.personId();
        return new GenericRecordPerson(
                personId,
                this.name(nameParser),
                this.gender(),
                this.events(personId, burialDate, places),
                this.notes());
    }

    Set<Event> events(final PersonId personId, final ExactDate burialDate, final PlaceProvider places) {
        final var events = super.events(personId, burialDate);
        events.add(this.burial(personId, burialDate));
        if (deathDate != null) events.add(this.death(personId, deathDate.resolve()));
        if (residence != null) events.add(this.residence(personId, Functions.ifNonNull(deathDate, XmlExactDate::resolve, burialDate), places.require(new PlaceId(residence))));
        return events;
    }

    Residence residence(final PersonId personId, final DateRange burialDate, final Place place) {
        return new GenericResidence(personId, BeforeDate.strictlyBefore(burialDate), place);
    }

    Death death(final PersonId personId, final ExactDate date) {
        return new GenericDeath(personId, date);
    }

    Burial burial(final PersonId personId, final ExactDate date) {
        return new ChurchBurial(personId, date, this.age());
    }

    int numIndividuals() {
        return 1 + (relationships == null ? 0 : relationships.size()); //FIXME count unique
    }

}
