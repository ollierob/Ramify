package net.ramify.model.record.xml.record.death;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.family.SinglePersonFamily;
import net.ramify.model.family.xml.XmlRelationship;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.civil.GenericDeathRecord;
import net.ramify.model.record.collection.RecordSet;
import net.ramify.model.record.type.DeathRecord;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonOnDateRecord;
import net.ramify.model.record.xml.record.XmlRecord;
import net.ramify.utils.objects.Functions;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

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
        final var deathPlace = Functions.ifNonNull(this.deathPlace, PlaceId::new, recordSetCovers);
        return new GenericDeathRecord(
                this.recordId(),
                recordSet,
                date,
                deathPlace,
                this.family(person, context, date));
    }

    Family family(final Person person, final RecordContext context, final ExactDate date) {
        if (relationships == null || relationships.isEmpty()) return new SinglePersonFamily(person);
        final var builder = new FamilyBuilder();
        builder.addPerson(person);
        relationships.forEach(relationship -> relationship.addRelationship(person, builder, context.nameParser(), date));
        return builder.build();
    }

    @Override
    protected Set<Event> events(final PersonId personId, final DateRange date) {
        final var events = super.events(personId, date);
        events.add(new GenericDeath(personId, date));
        return events;
    }

    public ExactDate deathDate() {
        return deathDate.resolve();
    }

    public int numIndividuals() {
        return 1; //TODO family
    }

}
