package net.ramify.model.person.xml;

import net.ramify.model.date.DateRange;
import net.ramify.model.person.age.Age;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.time.Period;

@XmlType(namespace = XmlPerson.NAMESPACE)
@XmlSeeAlso({XmlExactAge.class})
public abstract class XmlAge {

    public abstract Period age();

    public DateRange birthDate(final DateRange on) {
        return Age.birthDate(this.age(), on);
    }

}
