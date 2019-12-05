package net.ramify.model.place.xml.place.settlement;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({XmlCity.class, XmlTown.class, XmlVillage.class})
public abstract class XmlSettlement extends XmlPlace {

    @XmlAttribute(name = "countryIso")
    private String iso;

    @Nonnull
    @Deprecated
    @Override
    public PlaceId placeId() {
        return super.placeId();
    }

    @Override
    protected PlaceId placeId(final String id) {
        return this.placeId(id, CountryIso.valueOf(iso));
    }

    protected PlaceId placeId(final String id, final Place parent) {
        return this.placeId(id, CountryIso.readFrom(parent));
    }

    protected abstract PlaceId placeId(String id, CountryIso countryIso);

}
