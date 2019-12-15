package net.ramify.model.family.xml;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.ParentChild;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "daughter")
public class XmlDaughter extends XmlRelationship {

    @Override
    protected Relationship relationship(HasPersonId from, HasPersonId to) {
        return new ParentChild(from, to);
    }

    @Override
    protected Gender gender() {
        return Gender.FEMALE;
    }

}
