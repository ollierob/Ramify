package net.ramify.model.place.xml.place.area;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
public abstract class XmlArea<P extends Region> extends XmlPlace {

    private final Class<P> type;

    protected XmlArea(final Class<P> type) {
        this.type = type;
    }

    @Override
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new Spid(iso, type, id);
    }

    @Override
    protected abstract P place(
            Place parent,
            PlaceGroupId groupId,
            PlaceHistory history,
            PlaceParserContext context) throws Place.InvalidPlaceTypeException;

}
