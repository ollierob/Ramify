package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Mill;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "mill")
public class XmlMill extends XmlPlace {

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Mill.class, id);
    }

    @Override
    protected Mill place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Mill(this.placeId(), this.name(), parent);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return Collections.emptySet();
    }

}
