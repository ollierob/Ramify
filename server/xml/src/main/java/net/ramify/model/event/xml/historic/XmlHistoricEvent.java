package net.ramify.model.event.xml.historic;

import net.ramify.model.event.historic.HistoricEvent;
import net.ramify.model.event.xml.XmlEvent;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.SettlementOrRegion;
import net.ramify.model.place.xml.description.XmlLink;
import net.ramify.model.record.xml.RecordContext;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

import static net.ramify.utils.StringUtils.isBlank;

@XmlRootElement(namespace = XmlEvent.NAMESPACE, name = "historicEvent")
public class XmlHistoricEvent extends XmlEvent {

    @XmlAttribute(name = "title", required = true)
    private String title;

    @XmlAttribute(name = "place")
    private String placeId;

    @XmlAttribute(name = "region", required = false)
    private String regionId;

    @XmlElementRef(required = false)
    private XmlLink link;

    public HistoricEvent build(final RecordContext context) {
        return this.eventBuilder(context)
                .withPlace(this.place(context))
                .withLink(link)
                .toHistoric(title, this.region(context));
    }

    protected Place place(final RecordContext context) {
        if (isBlank(placeId)) return this.region(context);
        return context.places().require(new PlaceId(placeId));
    }

    @CheckForNull
    protected SettlementOrRegion region(final RecordContext context) {
        try {
            if (regionId == null) return null;
            return context.places().require(new PlaceId(regionId)).requireAs(SettlementOrRegion.class);
        } catch (Place.InvalidPlaceTypeException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
