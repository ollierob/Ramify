package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.settlement.Suburb;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "suburb")
public class XmlSuburb extends XmlSettlement {

    @Override
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new PlaceId(iso, Suburb.class, id);
    }

    @Nonnull
    @Override
    protected Suburb toPlace(final PlaceGroupId groupId, PlaceHistory history, PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Suburb(this.placeId(context), this.name(), groupId, history);
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return Collections.emptyList();
    }

}
