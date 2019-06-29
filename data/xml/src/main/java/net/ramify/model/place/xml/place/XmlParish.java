package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Parish;
import net.ramify.model.place.type.Region;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "parish")
public class XmlParish extends XmlPlace {

    @XmlElements({
            @XmlElement(name = "township", type = XmlTownship.class),
            @XmlElement(name = "town", type = XmlTown.class)
    })
    private List<XmlPlace> children;

    @Override
    PlaceId placeId(final String id) {
        return new Spid(Parish.class, id);
    }

    @Override
    Parish place(final PlaceId id, final String name, final Place parent) {
        return new Parish(id, name, parent.requireAs(Region.class));
    }

    @Override
    void addPlaces(final Set<PlaceId> places) {
        places.add(this.placeId());
        children.forEach(region -> region.addPlaces(places));
    }

}
