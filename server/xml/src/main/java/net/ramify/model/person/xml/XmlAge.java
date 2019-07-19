package net.ramify.model.person.xml;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.time.Period;

@XmlType(namespace = XmlPerson.NAMESPACE)
@XmlSeeAlso({XmlExactAge.class})
public abstract class XmlAge {

    public abstract Period age();

}
