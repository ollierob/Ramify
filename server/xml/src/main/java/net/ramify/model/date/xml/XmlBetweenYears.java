package net.ramify.model.date.xml;

import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(namespace = XmlDateRange.NAMESPACE)
public class XmlBetweenYears extends XmlDateRange {

    @XmlAttribute(name = "start", required = true)
    private int startYear;

    @XmlAttribute(name = "end", required = true)
    private int endYear;

    @Override
    public DateRange resolve(final DateParser parser) {
        return new ClosedDateRange(LocalDate.ofYearDay(startYear, 1), LocalDate.of(endYear, 12, 31));
    }

}
