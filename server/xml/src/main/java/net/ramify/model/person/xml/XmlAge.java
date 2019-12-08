package net.ramify.model.person.xml;

import net.ramify.model.date.DateRange;
import net.ramify.model.person.age.Age;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = XmlPerson.NAMESPACE)
@XmlSeeAlso({XmlExactAge.class, XmlApproximateAge.class, XmlAgeRange.class})
public abstract class XmlAge {

    public abstract Age age();

    public DateRange birthDate(final DateRange on) {
        return this.age().birthDate(on);
    }

}
