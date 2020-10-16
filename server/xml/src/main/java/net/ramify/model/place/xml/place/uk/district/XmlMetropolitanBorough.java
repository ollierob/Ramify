package net.ramify.model.place.xml.place.uk.district;

import com.google.common.base.MoreObjects;
import net.ramify.model.date.ExactDate;
import net.ramify.model.date.InYears;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountrySubdivisionIso;
import net.ramify.model.place.region.district.MetropolitanBorough;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.region.XmlRegion;
import net.ramify.model.place.xml.place.settlement.XmlCity;
import net.ramify.model.place.xml.place.settlement.XmlTown;
import net.ramify.model.place.xml.place.settlement.XmlVillage;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Month;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "metropolitanBorough")
public class XmlMetropolitanBorough extends XmlRegion<MetropolitanBorough> {

    static final PlaceHistory MODERN_HISTORY = new DefaultPlaceHistory(ExactDate.on(1974, Month.APRIL, 1), null);
    static final PlaceHistory LONDON_HISTORY = new DefaultPlaceHistory(new InYears(1900), new InYears(1965));

    @XmlElementRefs({
            @XmlElementRef(type = XmlCivilParish.class),
            @XmlElementRef(type = XmlCity.class),
            @XmlElementRef(type = XmlTown.class),
            @XmlElementRef(type = XmlVillage.class)
    })
    private List<XmlPlace> children;

    @XmlAttribute(name = "iso")
    private String iso;

    XmlMetropolitanBorough() {
        super(MetropolitanBorough.class);
    }

    @Override
    protected MetropolitanBorough toPlace(final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new MetropolitanBorough(this.placeId(context), this.name(), groupId, this.iso(), history);
    }

    @Override
    protected PlaceHistory history(final PlaceParserContext context) {
        return MoreObjects.firstNonNull(super.history(context), MODERN_HISTORY);
    }

    protected CountrySubdivisionIso iso() {
        return CountrySubdivisionIso.valueOf(iso);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return children;
    }

}
