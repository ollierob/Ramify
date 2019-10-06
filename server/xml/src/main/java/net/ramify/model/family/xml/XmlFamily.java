package net.ramify.model.family.xml;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import net.ramify.model.ParserContext;
import net.ramify.model.family.Family;
import net.ramify.model.family.FamilyBuilder;
import net.ramify.model.person.Person;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.provider.PersonProvider;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;
import java.util.Map;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "family")
public class XmlFamily {

    public static final String NAMESPACE = "http://ramify.net/families";

    @XmlElementRef
    private List<XmlFamilyPerson> people;

    public Family toFamily(final ParserContext context) {
        final var people = this.buildPeople(context);
        return this.buildFamily(people);
    }

    private FamilyPersonProvider buildPeople(final ParserContext context) {
        Preconditions.checkArgument(people != null && !people.isEmpty());
        final var provider = new FamilyPersonProvider();
        people.forEach(xmlPerson -> provider.add(xmlPerson.toPerson(context)));
        return provider;
    }

    private Family buildFamily(final FamilyPersonProvider provider) {
        Preconditions.checkArgument(people != null && !people.isEmpty());
        final var builder = new FamilyBuilder();
        people.forEach(xmlPerson -> {
            final var self = provider.require(xmlPerson.personId());
            final var relationships = xmlPerson.relationships(self, provider);
            relationships.forEach(relationship -> builder.addRelationship(relationship, provider));
        });
        return builder.build();
    }

    @XmlTransient
    private static class FamilyPersonProvider implements PersonProvider {

        private final Map<PersonId, Person> people = Maps.newHashMap();

        @CheckForNull
        @Override
        public Person get(final PersonId id) {
            return people.get(id);
        }

        void add(final Person person) {
            people.put(person.personId(), person);
        }

    }

}
