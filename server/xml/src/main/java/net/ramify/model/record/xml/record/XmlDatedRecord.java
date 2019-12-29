package net.ramify.model.record.xml.record;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.utils.objects.Functions;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;

public abstract class XmlDatedRecord extends XmlRecord {

    @XmlElementRef(namespace = XmlDateRange.NAMESPACE, required = false)
    private XmlDateRange date;

    @Nonnull
    protected DateRange date(final RecordContext context) {
        return Functions.ifNonNull(date, d -> d.resolve(context.dateParser()), context.recordDate());
    }

}
