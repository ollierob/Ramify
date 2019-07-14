package net.ramify.model.family.xml;

import net.ramify.model.person.Person;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.ChildParent;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "father")
public class XmlFather extends XmlRelationship {

    @Override
    protected Relationship relationship(final Person from, final Person to) {
        return new ChildParent(from, to);
    }

    @Override
    protected Gender gender() {
        return Gender.MALE;
    }

}
