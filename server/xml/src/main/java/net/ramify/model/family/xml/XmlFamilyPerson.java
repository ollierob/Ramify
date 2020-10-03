package net.ramify.model.family.xml;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;
import net.ramify.model.event.xml.person.XmlPersonEvent;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.provider.PersonProvider;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.model.record.xml.record.XmlPersonRecord;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.RelationshipFactory;
import net.ramify.model.relationship.type.Married;
import net.ramify.model.relationship.type.ParentChild;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static net.meerkat.collections.Collections.isEmpty;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "person")
public class XmlFamilyPerson extends XmlPersonRecord {

    @XmlElement(name = "parent", namespace = XmlFamily.NAMESPACE)
    private List<String> parents;

    @XmlElement(name = "spouse", namespace = XmlFamily.NAMESPACE)
    private List<String> spouses;

    @XmlElementRef(required = false)
    private List<XmlRelationship> relationships;

    @XmlElement(name = "label", namespace = XmlFamily.NAMESPACE)
    private Set<String> labels;

    protected Person toPerson(final RecordContext context) {
        final var personId = this.personId();
        return new GenericRecordPerson(
                personId,
                this.name(context.nameParser()),
                this.gender(),
                this.events(personId, context),
                this.features())
                .withNotes(this.notes())
                .withLabels(labels);
    }

    protected Set<Relationship> relationships(final Person self, final XmlFamily.FamilyPersonProvider people, final RecordContext context) {
        final var relationships = Sets.<Relationship>newHashSet();
        relationships.addAll(relatives(self, people, parents, ParentChild::new));
        relationships.addAll(relatives(self, people, spouses, Married::new));
        relationships.addAll(relatives(self, this.events()));
        for (final var relationship : this.relationships()) {
            people.add(relationship.toPerson(context));
            relationships.add(relationship.relationshipFrom(self, context));
        }
        return relationships;
    }

    private List<XmlRelationship> relationships() {
        return MoreObjects.firstNonNull(relationships, Collections.emptyList());
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

    private static Collection<Relationship> relatives(final Person self, final Collection<? extends XmlPersonEvent> events) {
        if (isEmpty(events)) return Collections.emptyList();
        return events.stream()
                .map(event -> event.inferredRelationships(self))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public int numPeople() {
        return 1
                + (parents == null ? 0 : parents.size())
                + (spouses == null ? 0 : spouses.size())
                + (relationships == null ? 0 : relationships.size());
    }

}
