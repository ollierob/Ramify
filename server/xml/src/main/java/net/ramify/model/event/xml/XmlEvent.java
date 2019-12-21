package net.ramify.model.event.xml;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.event.EventBuilder;
import net.ramify.model.event.EventId;
import net.ramify.model.record.xml.RecordContext;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "event")
public abstract class XmlEvent {

    public static final String NAMESPACE = "http://ramify.net/events";

    @XmlAttribute(name = "id")
    private String eventId = UUID.randomUUID().toString();

    @XmlElementRef(namespace = XmlDateRange.NAMESPACE)
    private XmlDateRange date;

    @XmlElement(name = "description", namespace = XmlEvent.NAMESPACE)
    private String description;

    @Nonnull
    protected EventId eventId() {
        return new EventId(eventId);
    }

    protected DateRange date(final RecordContext context) {
        return date == null ? context.recordDate() : date.resolve(context.dateParser());
    }

    protected EventBuilder eventBuilder(final RecordContext context) {
        return this.eventBuilder(this.eventId(), this.date(context))
                .withDescription(description);
    }

    protected EventBuilder eventBuilder(final EventId eventId, final DateRange date) {
        return EventBuilder.builder(eventId, date);
    }

}
