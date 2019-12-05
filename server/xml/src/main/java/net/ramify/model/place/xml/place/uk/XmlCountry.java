package net.ramify.model.place.xml.place.uk;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "country")
class XmlCountry extends XmlPlace {

    @XmlAttribute(name = "iso", required = true)
    private String iso;

    @XmlElementRef
    private List<XmlCounty> counties;

    @Nonnull
    protected Spid placeId(final String id, final CountryIso iso) {
        return new Spid(iso, Country.class, id);
    }

    @Override
    protected Place place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Preconditions.checkArgument(parent == null);
        return new Country(this.placeId(context), this.name(), this.iso());
    }

    protected CountryIso iso() {
        return CountryIso.valueOf(iso);
    }

    @Override
    protected Collection<XmlCounty> children() {
        return MoreObjects.firstNonNull(counties, Collections.emptyList());
    }

}
