package net.ramify.model.place.xml.place;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.type.Region;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
abstract class XmlArea<P extends Region> extends XmlPlace {

    private final Class<P> type;

    XmlArea(final Class<P> type) {
        this.type = type;
    }

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(type, id);
    }

    @Override
    protected abstract P place(Place parent) throws Place.InvalidPlaceTypeException;

}