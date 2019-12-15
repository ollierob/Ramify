package net.ramify.model.place.xml.place.uk.district;

import com.google.common.base.MoreObjects;
import net.ramify.model.date.InYears;
import net.ramify.model.place.DefaultPlaceHistory;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.district.MunicipalBorough;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.settlement.XmlCity;
import net.ramify.model.place.xml.place.settlement.XmlTown;
import net.ramify.model.place.xml.place.settlement.XmlVillage;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.annotation.CheckForNull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static net.ramify.model.place.xml.place.uk.XmlUkPlace.GB_ENG;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "municipalBorough")
public class XmlMunicipalBorough extends XmlArea<MunicipalBorough> {

    private static final PlaceHistory DEFAULT_ENGLAND_HISTORY = new DefaultPlaceHistory(new InYears(1833), new InYears(1974));
    private static final PlaceHistory DEFAULT_IRELAND_HISTORY = new DefaultPlaceHistory(new InYears(1840), new InYears(1973));

    @XmlElementRefs({
            @XmlElementRef(type = XmlCivilParish.class),
            @XmlElementRef(type = XmlCity.class),
            @XmlElementRef(type = XmlTown.class),
            @XmlElementRef(type = XmlVillage.class)
    })
    private List<XmlPlace> children;

    XmlMunicipalBorough() {
        super(MunicipalBorough.class);
    }

    @Override
    protected MunicipalBorough place(Place parent, PlaceGroupId groupId, PlaceHistory history, PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new MunicipalBorough(this.placeId(context), this.name(), parent.requireAs(Region.class), groupId, null, this.history(context));
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

    @Override
    protected PlaceHistory history(final PlaceParserContext context) {
        return MoreObjects.firstNonNull(super.history(context), this.defaultHistory(context.countryIso()));
    }

    protected PlaceHistory defaultHistory(final CountryIso iso) {
        if (GB_ENG.equals(iso)) return DEFAULT_ENGLAND_HISTORY;
        return null;
    }

}
