package net.ramify.model.date.xml;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = XmlDateRange.NAMESPACE)
@XmlSeeAlso({XmlBeforeDate.class, XmlInYear.class, XmlExactDate.class, XmlInQuarter.class, XmlBetweenYears.class, XmlInMonth.class})
public abstract class XmlDateRange {

    public static final String NAMESPACE = "http://ramify.net/dates";

    public abstract DateRange resolve(DateParser parser);

}
