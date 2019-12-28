package net.ramify.model.place.xml.place.uk.manor;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.manor.Manor;
import net.ramify.model.place.type.Region;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.region.XmlForest;
import net.ramify.model.place.xml.place.region.XmlRegion;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "manor")
public class XmlManor extends XmlRegion<Manor> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlManor.class),
            @XmlElementRef(type = XmlGraveship.class),
            @XmlElementRef(type = XmlForest.class)
    })
    private List<XmlPlace> children;

    XmlManor() {
        super(Manor.class);
    }

    @Override
    protected Manor toPlace(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new Manor(this.placeId(context), this.name(), parent.requireAs(Region.class), groupId, history);
    }

    @Override
    protected PlaceHistory history(final PlaceParserContext context) {
        return MoreObjects.firstNonNull(super.history(context), Manor.DEFAULT_HISTORY);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
