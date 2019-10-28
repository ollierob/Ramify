package net.ramify.model.place.xml.place.settlement;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.settlement.Village;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "village")
public class XmlVillage extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlStreet.class),
            @XmlElementRef(type = XmlChurch.class),
            @XmlElementRef(type = XmlMill.class),
            @XmlElementRef(type = XmlSchool.class),
            @XmlElementRef(type = XmlInn.class),
            @XmlElementRef(type = XmlGraveyard.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Village.class, id);
    }

    @Override
    protected Village place(final Place parent, final PlaceGroupId groupId) throws Place.InvalidPlaceTypeException {
        return new Village(this.placeId(), this.name(), parent, groupId);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
