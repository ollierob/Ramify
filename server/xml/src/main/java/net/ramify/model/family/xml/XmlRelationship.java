package net.ramify.model.family.xml;

import net.ramify.model.event.Event;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.record.GenericRecordPerson;
import net.ramify.model.relationship.Relationship;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Set;
import java.util.UUID;

@XmlSeeAlso({XmlFather.class, XmlMother.class})
public abstract class XmlRelationship {

    @XmlAttribute(name = "name", required = false)
    private String name;

    public void addRelationship(final Person from, final FamilyBuilder builder, final NameParser nameParser, final Set<? extends Event> events) {
        final var to = this.toPerson(nameParser, events);
        builder.addRelationship(from, to, this::relationship);
    }

    protected abstract Relationship relationship(HasPersonId from, HasPersonId to);

    protected abstract Gender gender();

    @CheckForNull
    protected Name name(final NameParser nameParser) {
        return Functions.ifNonNull(name, nameParser::parse);
    }

    protected PersonId personId() {
        return new PersonId(UUID.randomUUID().toString());
    }

    protected Person toPerson(final NameParser nameParser, final Set<? extends Event> events) {
        return new GenericRecordPerson(
                this.personId(),
                this.name(nameParser),
                this.gender(),
                events,
                null);
    }

}
