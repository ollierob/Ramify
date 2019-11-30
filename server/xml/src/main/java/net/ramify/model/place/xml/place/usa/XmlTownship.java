package net.ramify.model.place.xml.place.usa;

import com.google.common.base.MoreObjects;
import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.Township;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.settlement.XmlCity;
import net.ramify.model.place.xml.place.settlement.XmlTown;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "township")
class XmlTownship extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlCity.class),
            @XmlElementRef(type = XmlTown.class),
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(Township.class, id);
    }

    @Override
    protected Place place(final Place parent, final PlaceGroupId groupId, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new Township(this.placeId(), this.name(), parent, groupId);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
