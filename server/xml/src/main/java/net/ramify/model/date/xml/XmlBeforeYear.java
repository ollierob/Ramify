package net.ramify.model.date.xml;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlDateRange.NAMESPACE)
public class XmlBeforeYear extends XmlDateRange {

    @XmlAttribute(name = "year", required = true)
    private int year;

    @Override
    public DateRange resolve(final DateParser parser) {
        return BeforeDate.strictlyBefore(year);
    }

}