package net.ramify.model.place.xml.place.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.region.district.RuralDistrict;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "ruralDistrict")
public class XmlRuralDistrict extends XmlArea<RuralDistrict> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlCivilParish.class),
    })
    private List<XmlPlace> children;

    XmlRuralDistrict() {
        super(RuralDistrict.class);
    }

    @Override
    protected RuralDistrict place(final Place parent, final PlaceGroupId groupId) throws Place.InvalidPlaceTypeException {
        return new RuralDistrict(this.placeId(), this.name(), parent, groupId);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return children;
    }

}
