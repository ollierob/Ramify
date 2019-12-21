package net.ramify.model.place.xml.place.usa;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.Township;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.region.XmlRegion;
import net.ramify.model.place.xml.place.settlement.XmlCity;
import net.ramify.model.place.xml.place.settlement.XmlTown;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "township")
class XmlTownship extends XmlRegion<Township> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlCity.class),
            @XmlElementRef(type = XmlTown.class),
    })
    private List<XmlPlace> children;

    public XmlTownship() {
        super(Township.class);
    }

    @Override
    protected Township place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new Township(this.placeId(context), this.name(), parent.requireAs(Region.class), groupId, history);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
