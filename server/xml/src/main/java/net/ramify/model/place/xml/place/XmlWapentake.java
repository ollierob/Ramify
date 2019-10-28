package net.ramify.model.place.xml.place;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.region.Wapentake;
import net.ramify.model.place.xml.place.manor.XmlManor;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "wapentake")
class XmlWapentake extends XmlArea<Wapentake> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlParish.class),
            @XmlElementRef(type = XmlManor.class)
    })
    private List<XmlPlace> children;

    XmlWapentake() {
        super(Wapentake.class);
    }

    @Override
    protected Wapentake place(final Place parent, final PlaceGroupId groupId) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new Wapentake(this.placeId(), this.name(), parent, groupId);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
