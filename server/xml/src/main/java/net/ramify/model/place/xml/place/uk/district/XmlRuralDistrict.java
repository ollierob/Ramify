package net.ramify.model.place.xml.place.uk.district;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.district.RuralDistrict;
import net.ramify.model.place.region.district.UrbanDistrict;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.region.XmlRegion;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "ruralDistrict")
public class XmlRuralDistrict extends XmlRegion<RuralDistrict> {

    public static final PlaceHistory DEFAULT_HISTORY = UrbanDistrict.HISTORY;

    @XmlElementRefs({
            @XmlElementRef(type = XmlCivilParish.class),
    })
    private List<XmlPlace> children;

    XmlRuralDistrict() {
        super(RuralDistrict.class);
    }

    @Override
    protected RuralDistrict toPlace(final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new RuralDistrict(this.placeId(context), this.name(), groupId, history);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return children;
    }

    @Override
    protected PlaceHistory history(final PlaceParserContext context) {
        return MoreObjects.firstNonNull(super.history(context), DEFAULT_HISTORY);
    }

}
