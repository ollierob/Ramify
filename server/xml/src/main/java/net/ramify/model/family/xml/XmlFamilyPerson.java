package net.ramify.model.family.xml;

import com.google.common.collect.Sets;
import net.ramify.model.ParserContext;
import net.ramify.model.event.Event;
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
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "person")
public class XmlFamilyPerson extends XmlPersonRecord {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlElement(name = "father")
    private List<String> parents;

    @XmlElement(name = "spouse")
    private List<String> spouses;

    @Override
    protected PersonId personId() {
        return new PersonId(id);
    }

    protected Person toPerson(final ParserContext context) {
        return new GenericRecordPerson(
                this.personId(),
                this.name(context.nameParser()),
                this.gender(),
                this.events(),
                this.notes());
    }

    protected Set<Event> events() {
        return Collections.emptySet(); //TODO
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

}
