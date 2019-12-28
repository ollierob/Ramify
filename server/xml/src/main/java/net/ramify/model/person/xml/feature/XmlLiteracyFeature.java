package net.ramify.model.person.xml.feature;

import net.ramify.model.person.feature.Literacy;
import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.xml.XmlPerson;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPerson.NAMESPACE, name = "literacy")
public class XmlLiteracyFeature extends XmlPersonFeature {

    @XmlAttribute(name = "literate")
    private boolean literate;

    @Override
    public PersonFeature toFeature() {
        return new Literacy(literate);
    }

}
