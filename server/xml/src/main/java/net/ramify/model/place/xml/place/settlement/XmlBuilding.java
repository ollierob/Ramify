package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Collections;

@XmlTransient
abstract class XmlBuilding<P extends Place> extends XmlPlace {

    private final Class<P> type;

    XmlBuilding(final Class<P> type) {
        this.type = type;
    }

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(type, id);
    }

    @Override
    protected abstract P place(Place parent, PlaceGroupId groupId) throws Place.InvalidPlaceTypeException;

    @Override
    protected Collection<XmlPlace> children() {
        return Collections.emptySet();
    }

}
