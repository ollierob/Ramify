package net.ramify.model.person.xml;

import net.ramify.model.person.name.Name;
import net.ramify.model.person.name.NameParser;
import net.ramify.model.person.name.TranscribedName;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlPerson.NAMESPACE, name = "nameTranscription")
public class XmlNameTranscription extends XmlName {

    @XmlAttribute(name = "original", required = true)
    private String originalName;

    @XmlAttribute(name = "assumed", required = true)
    private String assumedName;

    @Override
    public Name build(final NameParser nameParser) {
        return new TranscribedName(originalName, nameParser.parse(assumedName));
    }

}
