package net.ramify.model.place.xml.regions;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Parish;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import java.util.List;
import java.util.Set;

public class XmlParish extends XmlPlace {

    @XmlElements({
            @XmlElement(name = "township", type = XmlTownship.class),
            @XmlElement(name = "town", type = XmlTown.class)
    })
    private List<XmlPlace> places;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Parish.class, id);
    }

    @Override
    Parish place(final PlaceId id, final Place parent) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    void addPlaces(final Set<PlaceId> places) {
        this.places.forEach(region -> region.addPlaces(places));
    }

}
