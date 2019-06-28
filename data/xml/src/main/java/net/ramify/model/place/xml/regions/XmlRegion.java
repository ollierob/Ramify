package net.ramify.model.place.xml.regions;

import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;

//@XmlSeeAlso({XmlCountry.class})
public abstract class XmlRegion implements HasPlaceId {

    @XmlAttribute(name = "id", required = true)
    private String id;

    XmlRegion() {
    }

    abstract PlaceId placeId(String id);

    @Nonnull
    @Override
    public PlaceId placeId() {
        return this.placeId(id);
    }

    abstract Place place(PlaceId id, Place parent);

}
