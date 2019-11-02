package net.ramify.model.family.xml;

import com.google.common.collect.Sets;
import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.event.Event;
import net.ramify.model.event.type.Death;
import net.ramify.model.event.type.death.GenericDeath;
import net.ramify.model.event.xml.XmlEvent;
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
import net.ramify.utils.collections.IterableUtils;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;
import java.util.Set;

@XmlSeeAlso({XmlFather.class, XmlMother.class})
public abstract class XmlRelationship {

    @XmlAttribute(name = "name", required = false)
    private String name;

    @XmlElementRef(required = false)
    private XmlName complexName;

    @XmlAttribute(name = "deceased")
    private Boolean deceased;

    @XmlElementRef
    private List<XmlEvent> events;

    public void addRelationship(final Person from, final FamilyBuilder builder, final RecordContext context, final DateRange date) {
        final var to = this.toPerson(date, context);
        builder.addRelationship(from, to, this::relationship);
    }

    protected abstract Relationship relationship(HasPersonId from, HasPersonId to);

    protected abstract Gender gender();

    protected Set<Event> events(final PersonId personId, final DateRange date, final RecordContext context) {
        final var events = Sets.<Event>newHashSet();
        if (this.events != null) this.events.forEach(event -> events.addAll(event.allEvents(personId, context)));
        if (Boolean.TRUE.equals(deceased) && !IterableUtils.has(events, Death.class)) events.add(new GenericDeath(personId, BeforeDate.strictlyBefore(date)));
        return events;
    }

    @CheckForNull
    protected Name name(final NameParser parser) {
        if (complexName != null) return complexName.build(parser);
        return Functions.ifNonNull(name, parser::parse);
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

}
