package net.ramify.model.family.xml;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.ChildParent;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "mother")
public class XmlMother extends XmlRelationship {

    @Override
    protected Relationship relationship(final HasPersonId from, final HasPersonId to) {
        return new ChildParent(from, to);
    }

    @Override
    protected Gender gender() {
        return Gender.FEMALE;
    }

}
