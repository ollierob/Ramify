package net.ramify.model.place.xml.place.uk;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.Rape;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.settlement.XmlCity;
import net.ramify.model.place.xml.place.settlement.XmlTown;
import net.ramify.model.place.xml.place.uk.manor.XmlManor;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "rape")
class XmlRape extends XmlArea<Rape> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlParish.class),
            @XmlElementRef(type = XmlManor.class),
            @XmlElementRef(type = XmlCity.class),
            @XmlElementRef(type = XmlTown.class),
            @XmlElementRef(type = XmlHundred.class)
    })
    private List<XmlPlace> children;

    XmlRape() {
        super(Rape.class);
    }

    @Override
    protected Rape place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new Rape(this.placeId(context), this.name(), parent, groupId, history);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
