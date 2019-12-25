package net.ramify.model.place.xml.place.france;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlFrance.NAMESPACE, name = "country")
public class XmlFrance extends XmlPlace {

    public static final String NAMESPACE = XmlPlace.NAMESPACE + "/fr";

    @XmlElementRef
    private List<XmlRegion> regions;

    @Override
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new PlaceId(iso, Country.class, id);
    }

    @Nonnull
    @Override
    protected Country place(Place parent, PlaceGroupId groupId, PlaceHistory history, PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Country(this.placeId(context), this.name(), context.countryIso(), null, groupId, history);
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return regions;
    }

}
