package net.ramify.model.place.xml.place.uk.manor;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountryIso;
import net.ramify.model.place.region.manor.Graveship;
import net.ramify.model.place.region.manor.Manor;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "graveship")
class XmlGraveship extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlManor.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new PlaceId(iso, Graveship.class, id);
    }

    @Override
    protected Graveship place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new Graveship(this.placeId(context), this.name(), parent.requireAs(Manor.class), groupId, history);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
