package net.ramify.model.place.xml.place.usa;

import com.google.common.base.MoreObjects;
import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.State;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "state")
public class XmlState extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlCounty.class),
            @XmlElementRef(type = XmlParish.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id) {
        return new Spid(State.class, id);
    }

    @Nonnull
    @Override
    protected Place place(Place parent, PlaceGroupId groupId, ParserContext context) throws Place.InvalidPlaceTypeException {
        return new State(this.placeId(), this.name(), parent, groupId);
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
