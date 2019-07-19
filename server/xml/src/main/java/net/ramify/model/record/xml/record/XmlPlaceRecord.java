package net.ramify.model.record.xml.record;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.provider.PlaceProvider;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class XmlPlaceRecord extends XmlRecord {

    @XmlAttribute(name = "place", required = true)
    private String placeId;

    protected Place place(final PlaceProvider placeProvider) {
        return placeProvider.require(new PlaceId(placeId));
    }

}
