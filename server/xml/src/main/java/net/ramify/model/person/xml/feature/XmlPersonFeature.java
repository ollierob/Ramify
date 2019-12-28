package net.ramify.model.person.xml.feature;

import net.ramify.model.person.features.PersonFeature;
import net.ramify.model.person.xml.XmlPerson;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlSeeAlso({XmlLiteracyFeature.class, XmlHairColourFeature.class})
@XmlType(namespace = XmlPerson.NAMESPACE)
public abstract class XmlPersonFeature {

    public abstract PersonFeature toFeature();

}
