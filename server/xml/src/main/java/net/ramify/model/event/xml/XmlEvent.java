package net.ramify.model.event.xml;

import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlExactDate;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.event.Event;
import net.ramify.model.person.PersonId;
import net.ramify.model.person.age.Age;
import net.ramify.model.person.xml.XmlAge;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.xml.RecordContext;
import net.ramify.utils.collections.SetUtils;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collections;
import java.util.Set;

@XmlSeeAlso({XmlBirthEvent.class, XmlDeathEvent.class, XmlResidenceEvent.class})
@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "event")
public abstract class XmlEvent {

    public static final String NAMESPACE = "http://ramify.net/events";

    @XmlAttribute(name = "placeId", required = false)
    private String placeId;

    @XmlElements({
            @XmlElement(name = "inYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
            @XmlElement(name = "onDate", type = XmlExactDate.class, namespace = XmlDateRange.NAMESPACE)
    })
    private XmlDateRange date;

    @XmlAttribute(name = "age")
    private Integer simpleAge;

    @XmlElementRef(required = false)
    private XmlAge complexAge;

    @Nonnull
    private DateRange date(final DateParser dates) {
        return date.resolve(dates);
    }

    protected DateRange date(final RecordContext context) {
        if (date != null) return this.date(context.dateParser());
        return context.recordDate();
    }

    @CheckForNull
    protected PlaceId placeId() {
        return Functions.ifNonNull(placeId, PlaceId::new);
    }

    @CheckForNull
    protected Place place(final RecordContext context) {
        final var placeId = this.placeId();
        return Functions.ifNonNull(placeId, context.places()::require);
    }

    public abstract Event toEvent(PersonId personId, RecordContext context);

    public abstract Set<Event> inferredEvents(PersonId personId, RecordContext context);

    public Set<Event> allEvents(final PersonId personId, final RecordContext context, final boolean inferredEvents) {
        final var event = this.toEvent(personId, context);
        return inferredEvents
                ? SetUtils.with(this.inferredEvents(personId, context), event)
                : Collections.singleton(event);
    }

    @CheckForNull
    protected Age age() {
        if (simpleAge != null) return Age.ofYears(simpleAge);
        if (complexAge != null) return complexAge.age();
        return null;
    }

}
