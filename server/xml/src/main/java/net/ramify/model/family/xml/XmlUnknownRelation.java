package net.ramify.model.family.xml;

import net.ramify.model.person.HasPersonId;
import net.ramify.model.person.gender.Gender;
import net.ramify.model.person.xml.XmlGender;
import net.ramify.model.relationship.Relationship;
import net.ramify.model.relationship.type.Unknown;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlFamily.NAMESPACE, name = "unknown")
public class XmlUnknownRelation extends XmlRelationship {

    @XmlAttribute(name = "gender", required = true)
    private XmlGender gender;

    @Override
    protected Relationship relationship(final HasPersonId from, final HasPersonId to) {
        return new Unknown(from, to);
    }

    @Override
    protected Gender gender() {
        return gender.gender();
    }

}
