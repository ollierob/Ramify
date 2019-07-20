package net.ramify.model.place.xml.place.district;

import net.ramify.model.place.Place;
import net.ramify.model.place.region.district.UrbanDistrict;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "urbanDistrict")
public class XmlUrbanDistrict extends XmlArea<UrbanDistrict> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlCivilParish.class),
    })
    private List<XmlPlace> children;

    XmlUrbanDistrict() {
        super(UrbanDistrict.class);
    }

    @Override
    protected UrbanDistrict place(final Place parent) throws Place.InvalidPlaceTypeException {
        return new UrbanDistrict(this.placeId(), this.name(), parent);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return children;
    }

}
