package net.ramify.model.place.xml.place.uk.district;

import com.google.common.base.MoreObjects;
import net.ramify.model.ParserContext;
import net.ramify.model.date.DateRange;
import net.ramify.model.date.InYears;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.region.district.UrbanDistrict;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "urbanDistrict")
public class XmlUrbanDistrict extends XmlArea<UrbanDistrict> {

    static final DateRange CREATED = new InYears(1894);
    static final DateRange ABOLISHED = new InYears(1974);

    @XmlElementRefs({
            @XmlElementRef(type = XmlCivilParish.class),
    })
    private List<XmlPlace> children;

    XmlUrbanDistrict() {
        super(CountryIso.GB, UrbanDistrict.class);
    }

    @Override
    protected UrbanDistrict place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new UrbanDistrict(this.placeId(), this.name(), parent, groupId, history);
    }

    @Override
    protected PlaceHistory history(final ParserContext context) {
        return MoreObjects.firstNonNull(super.history(context), UrbanDistrict.HISTORY);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return children;
    }

}
