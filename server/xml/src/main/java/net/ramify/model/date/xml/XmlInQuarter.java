package net.ramify.model.date.xml;

import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

@XmlRootElement(namespace = XmlDateRange.NAMESPACE, name = "inQuarter")
public class XmlInQuarter extends XmlDateRange {

    @XmlAttribute(name = "year")
    private int year;

    @XmlAttribute(name = "quarter")
    private int quarter;

    @Override
    public DateRange resolve(final DateParser parser) {
        switch (quarter) {
            case 1:
                return new ClosedDateRange(LocalDate.of(year, 1, 1), LocalDate.of(year, 3, 31));
            case 2:
                return new ClosedDateRange(LocalDate.of(year, 4, 1), LocalDate.of(year, 6, 30));
            case 3:
                return new ClosedDateRange(LocalDate.of(year, 7, 1), LocalDate.of(year, 9, 30));
            case 4:
                return new ClosedDateRange(LocalDate.of(year, 10, 1), LocalDate.of(year, 12, 31));
            default:
                throw new IllegalStateException("Invalid quarter: " + quarter);
        }
    }

}
