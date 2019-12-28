package net.ramify.model.person.xml.feature;

import net.ramify.model.person.feature.Height;
import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.xml.XmlPerson;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPerson.NAMESPACE, name = "height")
public class XmlHairColourFeature extends XmlPersonFeature {

    @XmlAttribute(name = "cm")
    private int heightCm;

    @Override
    public PersonFeature toFeature() {
        return new Height(heightCm);
    }

}
