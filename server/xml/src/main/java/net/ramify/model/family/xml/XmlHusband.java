package net.ramify.model.family.xml;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.Married;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "husband")
public class XmlHusband extends XmlRelationship {

    @Override
    protected Relationship relationship(final HasPersonId from, final HasPersonId to) {
        return new Married(from, to);
    }

    @Override
    protected Gender gender() {
        return Gender.MALE;
    }

}
