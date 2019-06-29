package net.ramify.model.date.xml;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://ramify.net/dates", name = "dateRange")
@XmlSeeAlso({XmlBeforeDate.class})
public abstract class XmlDateRange {

    public abstract DateRange resolve(DateParser parser);

}
