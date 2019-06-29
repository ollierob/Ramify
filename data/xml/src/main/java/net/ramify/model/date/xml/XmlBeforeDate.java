package net.ramify.model.date.xml;

import net.ramify.model.date.BeforeDate;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "before")
public class XmlBeforeDate extends XmlDateRange {

    @XmlAttribute(name = "date", required = true)
    private String date;

    @Override
    public DateRange resolve(final DateParser parser) {
        return BeforeDate.strictlyBefore(parser.parse(date));
    }

}
