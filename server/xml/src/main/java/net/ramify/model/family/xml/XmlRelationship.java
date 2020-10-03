package net.ramify.model.family.xml;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.EventBuilder;
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
import net.ramify.model.person.xml.name.XmlName;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.relationship.Relationship;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;
import java.util.UUID;

@XmlSeeAlso({XmlFather.class, XmlMother.class, XmlSon.class, XmlDaughter.class, XmlHusband.class, XmlWife.class, XmlUnknownRelation.class})
public abstract class XmlRelationship {

    @XmlAttribute(name = "personId")
    private String id = UUID.randomUUID().toString();

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

    public void addRelationship(final Person from, final FamilyBuilder builder, final RecordContext context) {
        final var to = this.toPerson(context);
        builder.addRelationship(from, to, this::relationship);
    }

    protected Relationship relationshipFrom(final Person from, final RecordContext context) {
        return this.relationship(from, this.toPerson(context));
    }

    protected abstract Relationship relationship(HasPersonId from, HasPersonId to);

    protected abstract Gender gender();

    protected MutablePersonEventSet events(final PersonId personId, final RecordContext context) {
        final var events = new MutablePersonEventSet(context.uniqueEventMerger());
        if (this.events != null) this.events.forEach(event -> events.addAll(event.allEvents(personId, context, true)));
        if (deceased != Boolean.TRUE) events.add(this.inferMention(personId, context.recordDate()));
        if (deceased == Boolean.TRUE) events.add(this.inferDeath(personId, context.recordDate()));
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
        return new PersonId(id);
    }

    protected Person toPerson(final RecordContext context) {
        final var id = this.personId();
        final var events = this.events(id, context);
        return new GenericRecordPerson(
                this.personId(),
                this.name(context.nameParser()),
                this.gender(),
                events);
    }

}
