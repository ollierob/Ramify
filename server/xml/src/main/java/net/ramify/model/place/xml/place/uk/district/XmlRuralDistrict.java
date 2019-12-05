package net.ramify.model.place.xml.place.uk.district;

import com.google.common.base.MoreObjects;
import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.district.RuralDistrict;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "ruralDistrict")
public class XmlRuralDistrict extends XmlArea<RuralDistrict> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlCivilParish.class),
    })
    private List<XmlPlace> children;

    XmlRuralDistrict() {
        super(RuralDistrict.class);
    }

    @Override
    protected RuralDistrict place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new RuralDistrict(this.placeId(context), this.name(), parent, groupId, history);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return children;
    }

    @Override
    protected PlaceHistory history(final ParserContext context) {
        return MoreObjects.firstNonNull(super.history(context), RuralDistrict.HISTORY);
    }

}
