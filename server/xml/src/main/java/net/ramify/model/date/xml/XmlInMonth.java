package net.ramify.model.date.xml;

import net.ramify.model.date.ClosedDateRange;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@XmlRootElement(namespace = XmlDateRange.NAMESPACE, name = "inMonth")
public class XmlInMonth extends XmlDateRange {

    @XmlAttribute(name = "year")
    private int year;

    @XmlAttribute(name = "month")
    private int month;

    @XmlAttribute(name = "earliestDay")
    private Integer earliestDay = 1;

    @XmlAttribute(name = "latestDay")
    private Integer latestDay;

    @Override
    public DateRange resolve(final DateParser parser) {
        return ClosedDateRange.of(this.earliest(), this.latest());
    }

    private LocalDate earliest() {
        return LocalDate.of(year, month, earliestDay);
    }

    private LocalDate latest() {
        if (latestDay != null) return LocalDate.of(year, month, latestDay);
        final var month = Month.of(this.month);
        final var length = month.length(Year.isLeap(year)); //FIXME should use Gregorian calendar here
        return LocalDate.of(year, month, length);
    }

}
