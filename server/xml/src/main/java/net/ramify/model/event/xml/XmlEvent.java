package net.ramify.model.event.xml;

import net.ramify.model.ParserContext;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.parse.DateParser;
import net.ramify.model.date.xml.XmlDateRange;
import net.ramify.model.date.xml.XmlInYear;
import net.ramify.model.event.Event;
import net.ramify.model.person.PersonId;
import net.ramify.model.place.PlaceId;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({XmlBirthEvent.class, XmlDeathEvent.class})
@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "event")
public abstract class XmlEvent {

    public static final String NAMESPACE = "http://ramify.net/events";

    @XmlAttribute(name = "placeId", required = false)
    private String placeId;

    @XmlElements({
            @XmlElement(name = "inYear", type = XmlInYear.class, namespace = XmlDateRange.NAMESPACE),
    })
    private XmlDateRange date;

    @Nonnull
    protected DateRange date(final DateParser dates) {
        return date.resolve(dates);
    }

    @CheckForNull
    protected PlaceId placeId() {
        return Functions.ifNonNull(placeId, PlaceId::new);
    }

    public abstract Event toEvent(PersonId personId, ParserContext context);

}
