package net.ramify.model.place.xml.place.uk;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.CountyOrSubdivision;
import net.ramify.model.place.region.Hundred;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.region.XmlRegion;
import net.ramify.model.place.xml.place.settlement.XmlTown;
import net.ramify.model.place.xml.place.uk.manor.XmlManor;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "hundred")
@XmlSeeAlso({XmlRape.class})
class XmlHundred extends XmlRegion<Hundred> {

    static final PlaceProto.PlaceType TYPE = PlaceProto.PlaceType.newBuilder().setName("Hundred").setCanPrefix(true).setCanSuffix(true).build();

    @XmlElementRefs({
            @XmlElementRef(type = XmlParish.class),
            @XmlElementRef(type = XmlManor.class),
            @XmlElementRef(type = XmlTown.class)
    })
    private List<XmlPlace> children;

    XmlHundred() {
        super(Hundred.class);
    }

    @Override
    protected Hundred place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new Hundred(this.placeId(context), this.name(), parent.requireAs(CountyOrSubdivision.class), groupId, history, TYPE);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
