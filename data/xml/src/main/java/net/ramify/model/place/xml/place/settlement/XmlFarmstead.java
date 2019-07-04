package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.building.Farmstead;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "farmstead")
public class XmlFarmstead extends XmlPlace {

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Farmstead.class, id);
    }

    @Override
    protected Farmstead place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new Farmstead(this.placeId(), this.name(), parent);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return Collections.emptySet();
    }

}
