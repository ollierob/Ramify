package net.ramify.model.date.xml;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;

import javax.xml.bind.annotation.XmlAttribute;

class XmlBeforeDate extends XmlDateRange {

    @XmlAttribute(name = "date")
    private String date;

    @Override
    public DateRange resolve() {
        return BeforeDate.strictlyBefore(date);
    }

}
