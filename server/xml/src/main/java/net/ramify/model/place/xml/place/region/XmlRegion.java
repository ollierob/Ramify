package net.ramify.model.place.xml.place.region;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class XmlRegion<P extends Region> extends XmlPlace {

    private final Class<P> type;

    protected XmlRegion(final Class<P> type) {
        this.type = type;
    }

    @Override
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new PlaceId(iso, type, id);
    }

    @Override
    protected abstract P toPlace(
            PlaceGroupId groupId,
            PlaceHistory history,
            PlaceParserContext context) throws Place.InvalidPlaceTypeException;

}
