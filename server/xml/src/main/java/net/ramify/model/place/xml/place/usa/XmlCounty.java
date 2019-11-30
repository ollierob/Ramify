package net.ramify.model.place.xml.place.usa;

import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.County;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "county")
class XmlCounty extends XmlPlace {

    @XmlAttribute(name = "iso")
    private String iso;

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(County.class, id);
    }

    @Override
    protected County place(final Place parent, final PlaceGroupId groupId, final ParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new County(this.placeId(), this.name(), parent, iso, groupId);
    }

    @Override
    protected Collection<XmlPlace> children() {
        //  return MoreObjects.firstNonNull(children, Collections.emptyList());
        return Collections.emptyList();
    }

}
