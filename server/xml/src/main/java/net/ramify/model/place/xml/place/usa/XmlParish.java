package net.ramify.model.place.xml.place.usa;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Parish;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.xml.PlaceParserContext;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "parish")
class XmlParish extends XmlCounty {

    @Override
    protected Spid placeId(final String id, final CountryIso iso) {
        return new Spid(iso, Parish.class, id);
    }

    @Override
    protected Parish place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Parish(this.placeId(context), this.name(), parent, groupId, history);
    }

}
