package net.ramify.model.place.xml.place.usa;

import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceHistory;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Parish;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "parish")
class XmlParish extends XmlCounty {

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Parish.class, id);
    }

    @Override
    protected Parish place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new Parish(this.placeId(), this.name(), parent, groupId, history);
    }

}
