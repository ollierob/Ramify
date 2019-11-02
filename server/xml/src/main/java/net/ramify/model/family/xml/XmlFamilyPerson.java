package net.ramify.model.family.xml;

import com.google.common.collect.Sets;
import net.ramify.model.ParserContext;
import net.ramify.model.event.Event;
import net.ramify.model.event.xml.XmlEvent;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.provider.PersonProvider;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.xml.record.XmlPersonRecord;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.RelationshipFactory;
import net.ramify.model.relationship.type.Married;
import net.ramify.model.relationship.type.ParentChild;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "person")
public class XmlFamilyPerson extends XmlPersonRecord {

    @XmlAttribute(name = "id")
    private String id = UUID.randomUUID().toString();

    @XmlElementRef
    private List<XmlEvent> events;

    @XmlElement(name = "parent", namespace = XmlFamily.NAMESPACE)
    private List<String> parents;

    @XmlElement(name = "spouse", namespace = XmlFamily.NAMESPACE)
    private List<String> spouses;

    @Override
    protected PersonId personId() {
        return new PersonId(id);
    }

    protected Person toPerson(final ParserContext context) {
        final var personId = this.personId();
        return new GenericRecordPerson(
                personId,
                this.name(context.nameParser()),
                this.gender(),
                this.events(personId, context),
                this.notes());
    }

    protected Set<Event> events(final PersonId personId, final ParserContext context) {
        if (events == null || events.isEmpty()) return Collections.emptySet();
        final var events = Sets.<Event>newHashSet();
        this.events.forEach(event -> events.addAll(event.allEvents(personId, context)));
        return events;
    }

    protected Set<Relationship> relationships(final Person self, final PersonProvider people) {
        final var relationships = Sets.<Relationship>newHashSet();
        relationships.addAll(relatives(self, people, parents, ParentChild::new));
        relationships.addAll(relatives(self, people, spouses, Married::new));
        return relationships;
    }

    private static Collection<Relationship> relatives(final Person self, final PersonProvider people, final Collection<String> ids, final RelationshipFactory factory) {
        if (ids == null || ids.isEmpty()) return Collections.emptySet();
        return ids.stream()
                .distinct()
                .map(PersonId::new)
                .map(people::require)
                .map(person -> factory.relationshipBetween(person, self))
                .collect(Collectors.toList());
    }

    public int numPeople() {
        return 1 + (parents == null ? 0 : parents.size()) + (spouses == null ? 0 : spouses.size());
    }

}
