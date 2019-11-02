package net.ramify.model.person.xml;

import com.google.common.base.MoreObjects;
import net.ramify.model.person.age.Age;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Period;

@XmlRootElement(namespace = XmlPerson.NAMESPACE, name = "approximateAge")
public class XmlApproximateAge extends XmlAge {

    @XmlAttribute(name = "years")
    private Integer years;

    @XmlAttribute(name = "months")
    private Integer months;

    @XmlAttribute(name = "days")
    private Integer days;

    @Override
    public Age age() {
        if (years == null && months == null && days == null) throw new UnsupportedOperationException("No age specified");
        if (months == null && days == null) return Age.ofYears(years);
        if (years == null && days == null) return Age.betweenInclusive(Period.ofMonths(months), Period.ofMonths(months + 1));
        if (years == null && months == null) return Age.exactly(Period.ofDays(days));
        return Age.exactly(Period.of(MoreObjects.firstNonNull(years, 0), MoreObjects.firstNonNull(months, 0), MoreObjects.firstNonNull(days, 0)));
    }

}
