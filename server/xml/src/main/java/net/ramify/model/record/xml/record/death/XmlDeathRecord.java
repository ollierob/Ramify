package net.ramify.model.record.xml.record.death;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.family.xml.XmlRelationship;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.GenericDeathRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.DeathRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateRecord;
import net.ramify.model.record.xml.record.XmlRecord;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Optional;

@XmlRootElement(namespace = XmlRecord.NAMESPACE, name = "death")
public class XmlDeathRecord extends XmlPersonOnDateRecord {

    @XmlElement(name = "deathDate", namespace = XmlDateRange.NAMESPACE)
    private XmlExactDate deathDate;

    @XmlAttribute(name = "deathPlace", namespace = XmlRecord.NAMESPACE)
    private String deathPlace;

    @XmlElementRef
    private List<XmlRelationship> relationships;

    @Nonnull
    public DeathRecord build(final PlaceId recordSetCovers, final RecordContext context, final RecordSet recordSet) {
        final var date = this.deathDate();
        final var person = this.person(date, context);
        final var deathPlace = this.deathPlace().orElse(recordSetCovers);
        return new GenericDeathRecord(
                this.recordId(),
                recordSet,
                date,
                deathPlace,
                this.family(person, context, date));
    }

    Family family(final Person person, final RecordContext context, final ExactDate date) {
        if (relationships == null || relationships.isEmpty()) return new SinglePersonFamily(person);
        final var builder = new FamilyBuilder(person);
        relationships.forEach(relationship -> relationship.addRelationship(person, builder, context, date));
        return builder.build();
    }

    @Override
    protected MutablePersonEventSet events(final PersonId personId, final DateRange date, final RecordContext context) {
        final var events = super.events(personId, date, context);
        events.add(this.death(personId, date, context));
        return events;
    }

    protected DeathEvent death(final PersonId personId, final DateRange date, final RecordContext context) {
        return this.eventBuilder(date)
                .withPlace(this.deathPlace(context))
                .toDeath(personId);
    }

    protected Optional<PlaceId> deathPlace() {
        return Optional.ofNullable(deathPlace).map(PlaceId::new);
    }

    protected Place deathPlace(final RecordContext context) {
        return this.deathPlace().map(context.places()::require).orElse(null);
    }

    public ExactDate deathDate() {
        return deathDate.resolve();
    }

    public int numIndividuals() {
        return 1; //TODO family
    }

}
