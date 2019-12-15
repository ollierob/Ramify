package net.ramify.model.place.xml.place.uk;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.County;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.settlement.XmlCity;
import net.ramify.model.place.xml.place.uk.district.XmlBorough;
import net.ramify.model.place.xml.place.uk.district.XmlLondonBorough;
import net.ramify.model.place.xml.place.uk.district.XmlMetropolitanBorough;
import net.ramify.model.place.xml.place.uk.district.XmlMunicipalBorough;
import net.ramify.model.place.xml.place.uk.district.XmlRuralDistrict;
import net.ramify.model.place.xml.place.uk.district.XmlUrbanDistrict;
import net.ramify.model.place.xml.place.uk.manor.XmlManor;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "county")
class XmlCounty extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlCity.class),
            @XmlElementRef(type = XmlParish.class),
            @XmlElementRef(type = XmlManor.class),
            @XmlElementRef(type = XmlHundred.class),
            @XmlElementRef(type = XmlWapentake.class),
            @XmlElementRef(type = XmlRape.class),
            @XmlElementRef(type = XmlLathe.class),
            @XmlElementRef(type = XmlWard.class),
            @XmlElementRef(type = XmlBorough.class),
            @XmlElementRef(type = XmlMetropolitanBorough.class),
            @XmlElementRef(type = XmlMunicipalBorough.class),
            @XmlElementRef(type = XmlUrbanDistrict.class),
            @XmlElementRef(type = XmlRuralDistrict.class),
            @XmlElementRef(type = XmlLondonBorough.class)
    })
    private List<XmlPlace> children;

    @XmlAttribute(name = "iso")
    private String iso;

    @Override
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new Spid(iso, County.class, id);
    }

    @Override
    protected County place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new County(this.placeId(context), this.name(), parent, this.iso(), groupId, history);
    }

    protected CountrySubdivisionIso iso() {
        return CountrySubdivisionIso.valueOf(iso);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
