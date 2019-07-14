package net.ramify.model.date.xml;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.parse.DateParser;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Month;

@XmlRootElement(namespace = XmlDateRange.NAMESPACE)
public class XmlExactDate extends XmlDateRange {

    @XmlAttribute(name = "year")
    private int year;

    @XmlAttribute(name = "month")
    private int month;

    @XmlAttribute(name = "dayOfMonth")
    private int dayOfMonth;

    @Nonnull
    public ExactDate resolve() {
        return ExactDate.on(year, Month.of(month), dayOfMonth);
    }

    @Override
    @Deprecated
    public DateRange resolve(final DateParser parser) {
        return this.resolve();
    }

}
