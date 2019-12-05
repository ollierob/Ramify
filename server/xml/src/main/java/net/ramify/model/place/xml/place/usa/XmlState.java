package net.ramify.model.place.xml.place.usa;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.region.State;
import net.ramify.model.place.region.iso.CountrySubdivisionIso;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "state")
public class XmlState extends XmlArea<State> {

    @XmlElementRefs({
            @XmlElementRef(type = XmlCounty.class),
            @XmlElementRef(type = XmlParish.class)
    })
    private List<XmlPlace> children;

    @XmlAttribute(name = "iso")
    private String iso;

    XmlState() {
        super(State.class);
    }

    @Nonnull
    @Override
    protected State place(Place parent, PlaceGroupId groupId, final PlaceHistory history, PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new State(this.placeId(context), this.name(), parent, this.iso(), groupId);
    }

    protected CountrySubdivisionIso iso() {
        return CountrySubdivisionIso.valueOf(iso);
    }

    @CheckForNull
    @Override
    protected Collection<? extends XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}
