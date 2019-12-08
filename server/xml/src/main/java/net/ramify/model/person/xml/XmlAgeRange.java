package net.ramify.model.person.xml;

import net.ramify.model.person.age.Age;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Period;

@XmlRootElement(namespace = XmlPerson.NAMESPACE, name = "ageRange")
public class XmlAgeRange extends XmlAge {

    @XmlAttribute(name = "minYears")
    private Integer minYears;

    @XmlAttribute(name = "maxYears")
    private Integer maxYears;

    @Override
    public Age age() {
        return Age.betweenInclusive(this.min(), this.max());
    }

    Period min() {
        if (minYears == null) return Period.ZERO;
        return Period.ofYears(minYears);
    }

    Period max() {
        if (maxYears == null) return Period.ZERO;
        return Period.of(maxYears + 1, 0, -1);
    }

}
