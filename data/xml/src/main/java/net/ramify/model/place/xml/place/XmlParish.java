package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Parish;
import net.ramify.model.place.type.Region;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "parish")
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
    Parish place(final Place parent) {
        return new Parish(this.placeId(), this.name(), parent.requireAs(Region.class));
    }

    @Override
    Collection<XmlPlace> children() {
        return children;
    }

}
