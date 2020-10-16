package net.ramify.model.place.xml.place.usa;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.region.Parish;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.xml.PlaceParserContext;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "parish")
class XmlParish extends XmlCounty {

    @Override
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new PlaceId(iso, Parish.class, id);
    }

    @Override
    protected Region toPlace(final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Parish(this.placeId(context), this.name(), groupId, history);
    }

}
