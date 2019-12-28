package net.ramify.model.place.xml.place.usa;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.iso.CountrySubdivisionIso;
import net.ramify.model.place.region.Country;
import net.ramify.model.place.region.State;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.region.XmlRegion;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlUsaPlace.NAMESPACE, name = "state")
public class XmlState extends XmlRegion<State> {

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
    protected State toPlace(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new State(this.placeId(context), this.name(), parent.requireAs(Country.class), this.iso(), groupId, history);
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
