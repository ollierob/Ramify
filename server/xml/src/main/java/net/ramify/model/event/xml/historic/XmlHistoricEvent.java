package net.ramify.model.event.xml.historic;

import net.ramify.model.event.historic.HistoricEvent;
import net.ramify.model.event.xml.XmlEvent;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.record.xml.RecordContext;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "historicEvent")
public class XmlHistoricEvent extends XmlEvent {

    @XmlAttribute(name = "title", required = true)
    private String title;

    @XmlAttribute(name = "place", required = true)
    private String placeId;

    @XmlElement(name = "description", namespace = XmlEvent.NAMESPACE, required = true)
    private String description;

    public HistoricEvent build(final RecordContext context) {
        return this.eventBuilder(context)
                .withPlace(this.place(context))
                .toHistoric(title, description);
    }

    protected Place place(final RecordContext context) {
        return context.places().require(new PlaceId(placeId));
    }

}
