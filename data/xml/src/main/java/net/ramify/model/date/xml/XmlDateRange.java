package net.ramify.model.date.xml;

import net.ramify.model.date.DateRange;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://ramify.net", name = "dateRange")
public class XmlDateRange {

    public DateRange resolve() {
        throw new UnsupportedOperationException();
    }

}
