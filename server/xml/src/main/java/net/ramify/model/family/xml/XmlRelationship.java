package net.ramify.model.family.xml;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.EventId;
import net.ramify.model.event.collection.MutablePersonEventSet;
import net.ramify.model.event.type.DeathEvent;
import net.ramify.model.event.type.LifeEvent;
import net.ramify.model.event.xml.person.XmlPersonEvent;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.person.xml.XmlName;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.relationship.Relationship;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlSeeAlso({XmlFather.class, XmlMother.class, XmlSon.class, XmlDaughter.class, XmlWife.class, XmlUnknownRelation.class})
public abstract class XmlRelationship {

    @XmlAttribute(name = "name")
    private String name;

    @XmlElementRef(required = false)
    private XmlName complexName;

    @XmlAttribute(name = "deceased")
    private Boolean deceased;

    @XmlAttribute(name = "occupation")
    private String occupation;

    @XmlElementRef
    private List<XmlPersonEvent> events;

    public void addRelationship(final Person from, final FamilyBuilder builder, final RecordContext context, final DateRange date) {
        final var to = this.toPerson(date, context);
        builder.addRelationship(from, to, this::relationship);
    }

    protected abstract Relationship relationship(HasPersonId from, HasPersonId to);

    protected abstract Gender gender();

    protected MutablePersonEventSet events(final PersonId personId, final DateRange date, final RecordContext context) {
        final var events = new MutablePersonEventSet(context.uniqueEventMerger());
        if (this.events != null) this.events.forEach(event -> events.addAll(event.allEvents(personId, context, true)));
        if (deceased != Boolean.TRUE) events.add(this.inferMention(personId, date));
        if (deceased == Boolean.TRUE) events.add(this.inferDeath(personId, date));
        return events;
    }

    protected EventBuilder eventBuilder(final DateRange date) {
        return EventBuilder.builderWithRandomId(date)
                .withOccupation(occupation);
    }

    protected LifeEvent inferMention(final PersonId personId, final DateRange date) {
        return this.eventBuilder(date).toFlourished(personId);
    }

    protected DeathEvent inferDeath(final PersonId personId, final DateRange date) {
        return this.eventBuilder(BeforeDate.strictlyBefore(date)).toDeath(personId);
    }

    @CheckForNull
    protected Name name(final NameParser parser) {
        if (complexName != null) return complexName.build(parser);
        return Functions.ifNonNull(name, parser::parse, Name.UNKNOWN);
    }

    protected PersonId personId() {
        return PersonId.random();
    }

    protected Person toPerson(final DateRange date, final RecordContext context) {
        final var id = this.personId();
        final var events = this.events(id, date, context);
        return new GenericRecordPerson(
                this.personId(),
                this.name(context.nameParser()),
                this.gender(),
                events,
                null);
    }

    protected EventId randomEventId() {
        return EventId.random();
    }

}
