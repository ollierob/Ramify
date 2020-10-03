package net.ramify.model.record.xml.record.death;

import com.google.common.base.MoreObjects;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.date.xml.XmlInMonth;
import net.ramify.model.date.xml.XmlInQuarter;
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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "burial")
public class XmlBurialRecord extends XmlPersonOnDateRecord {

    @XmlAttribute(name = "residence")
    private String residence;

    @XmlElements({
            @XmlElement(name = "buriedOn", namespace = XmlDateRange.NAMESPACE, type = XmlExactDate.class),
            @XmlElement(name = "buriedInMonth", namespace = XmlDateRange.NAMESPACE, type = XmlInMonth.class),
            @XmlElement(name = "buriedInQuarter", namespace = XmlDateRange.NAMESPACE, type = XmlInQuarter.class)
    })
    private XmlDateRange burialDate;

    @XmlElements({
            @XmlElement(name = "diedOn", namespace = XmlDateRange.NAMESPACE, type = XmlExactDate.class),
            @XmlElement(name = "diedInMonth", namespace = XmlDateRange.NAMESPACE, type = XmlInMonth.class),
            @XmlElement(name = "diedInQuarter", namespace = XmlDateRange.NAMESPACE, type = XmlInQuarter.class)
    })
    private XmlDateRange deathDate;

    @XmlAttribute(name = "deathPlace")
    private String deathPlace;

    @XmlElementRef
    private List<XmlRelationship> relationships;

    public BurialRecord build(final PlaceId burialPlaceId, final RecordContext context, final RecordSet recordSet) {
        Objects.requireNonNull(burialPlaceId, "burial place ID");
        final var burialDate = this.burialDate(context);
        final var family = this.family(context.onDate(burialDate), burialPlaceId);
        return new ChurchBurialRecord(
                this.recordId(),
                recordSet,
                burialDate,
                burialPlaceId,
                family);
    }

    DateRange burialDate(final RecordContext context) {
        if (burialDate != null) return burialDate.resolve(context.dateParser());
        return context.recordDate();
    }

    Family family(final RecordContext context, final PlaceId burialPlaceId) {
        final var person = this.person(context.nameParser(), context, burialPlaceId);
        final var builder = new FamilyBuilder(person);
        if (relationships != null) relationships.forEach(relationship -> relationship.addRelationship(person, builder, context));
        return builder.build();
    }

    Person person(final NameParser nameParser, final RecordContext context, final PlaceId burialPlaceId) {
        final var personId = this.personId();
        return new GenericRecordPerson(
                personId,
                this.name(nameParser),
                this.gender(),
                this.events(personId, context, burialPlaceId))
                .withNotes(this.notes());
    }

    MutablePersonEventSet events(final PersonId personId, final RecordContext context, final PlaceId burialPlaceId) {
        final var events = super.events(personId, context);
        final var burialDate = context.recordDate();
        events.add(this.burialEvent(personId, burialDate, context.places().require(burialPlaceId)));
        final var deathDate = this.deathDate(context);
        if (deathDate != null) events.add(this.deathEvent(personId, deathDate, context));
        if (residence != null) events.add(this.residenceEvent(personId, MoreObjects.firstNonNull(deathDate, BeforeDate.strictlyBefore(burialDate)), context));
        return events;
    }

    DateRange deathDate(final RecordContext context) {
        return deathDate == null ? null : deathDate.resolve(context.dateParser());
    }

    ResidenceEvent residenceEvent(final PersonId personId, final DateRange burialDate, final RecordContext context) {
        return this.eventBuilder(BeforeDate.strictlyBefore(burialDate))
                .withPlace(this.residencePlace(context))
                .toResidence(personId);
    }

    Place residencePlace(final RecordContext context) {
        return Optional.ofNullable(residence).map(PlaceId::new).map(context.places()::require).orElse(null);
    }

    DeathEvent deathEvent(final PersonId personId, final DateRange date, final RecordContext context) {
        return this.eventBuilder(date)
                .withPlace(this.deathPlace(context))
                .toDeath(personId);
    }

    Place deathPlace(final RecordContext context) {
        return Optional.ofNullable(deathPlace).map(PlaceId::new).map(context.places()::require).orElse(null);
    }

    BurialEvent burialEvent(final PersonId personId, final DateRange date, final Place burialPlace) {
        return this.eventBuilder(date)
                .withPlace(burialPlace)
                .toBurial(personId);
    }

    @Override
    public int numIndividuals() {
        return 1 + (relationships == null ? 0 : relationships.size()); //FIXME count unique
    }

}
