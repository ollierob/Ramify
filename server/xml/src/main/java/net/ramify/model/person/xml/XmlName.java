package net.ramify.model.person.xml;

import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = XmlPerson.NAMESPACE)
@XmlSeeAlso({XmlNameTranscription.class})
public abstract class XmlName {

    public abstract Name build(NameParser nameParser);

}
