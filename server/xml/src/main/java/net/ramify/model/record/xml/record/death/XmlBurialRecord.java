package net.ramify.model.record.xml.record.death;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.burial.BurialEvent;
import net.ramify.model.event.type.residence.ResidenceEvent;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.xml.XmlRelationship;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
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
import java.util.Objects;
import java.util.Optional;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "burial")
public class XmlBurialRecord extends XmlPersonOnDateRecord {

    @XmlAttribute(name = "residence")
    private String residence;

    @XmlAttribute(name = "year")
    private int year;

    @XmlAttribute(name = "month")
    private int month;

    @XmlAttribute(name = "day")
    private int dayOfMonth;

    @XmlElement(name = "deathDate", required = false, namespace = XmlDateRange.NAMESPACE)
    private XmlExactDate deathDate;

    @XmlAttribute(name = "deathPlace")
    private String deathPlace;

    @XmlElementRef
    private List<XmlRelationship> relationships;

    public BurialRecord build(final PlaceId burialPlaceId, final RecordContext context, final RecordSet recordSet) {
        Objects.requireNonNull(burialPlaceId, "burial place ID");
        final var burialDate = ExactDate.on(year, Month.of(month), dayOfMonth);
        return new ChurchBurialRecord(
                this.recordId(),
                recordSet,
                burialDate,
                burialPlaceId,
                this.family(burialDate, context.onDate(burialDate), burialPlaceId));
    }

    Family family(final ExactDate burialDate, final RecordContext context, final PlaceId burialPlaceId) {
        final var person = this.person(context.nameParser(), burialDate, context, burialPlaceId);
        final var builder = new FamilyBuilder(person);
        if (relationships != null) relationships.forEach(relationship -> relationship.addRelationship(person, builder, context, burialDate));
        return builder.build();
    }

    Person person(final NameParser nameParser, final ExactDate burialDate, final RecordContext context, final PlaceId burialPlaceId) {
        final var personId = this.personId();
        return new GenericRecordPerson(
                personId,
                this.name(nameParser),
                this.gender(),
                this.events(personId, burialDate, context, burialPlaceId),
                this.notes());
    }

    MutablePersonEventSet events(final PersonId personId, final ExactDate burialDate, final RecordContext context, final PlaceId burialPlaceId) {
        final var events = super.events(personId, burialDate, context);
        events.add(this.burial(personId, burialDate, context.places().require(burialPlaceId)));
        if (deathDate != null) events.add(this.death(personId, deathDate.resolve(), context));
        if (residence != null) events.add(this.residence(personId, Functions.ifNonNull(deathDate, XmlExactDate::resolve, burialDate), context));
        return events;
    }

    ResidenceEvent residence(final PersonId personId, final DateRange burialDate, final RecordContext context) {
        return this.eventBuilder(BeforeDate.strictlyBefore(burialDate))
                .withPlace(this.residencePlace(context))
                .toResidence(personId);
    }

    Place residencePlace(final RecordContext context) {
        return Optional.ofNullable(residence).map(PlaceId::new).map(context.places()::require).orElse(null);
    }

    DeathEvent death(final PersonId personId, final ExactDate date, final RecordContext context) {
        return this.eventBuilder(date)
                .withPlace(this.deathPlace(context))
                .toDeath(personId);
    }

    Place deathPlace(final RecordContext context) {
        return Optional.ofNullable(deathPlace).map(PlaceId::new).map(context.places()::require).orElse(null);
    }

    BurialEvent burial(final PersonId personId, final ExactDate date, final Place burialPlace) {
        return this.eventBuilder(date)
                .withPlace(burialPlace)
                .toBurial(personId);
    }

    @Override
    public int numIndividuals() {
        return 1 + (relationships == null ? 0 : relationships.size()); //FIXME count unique
    }

}
