package net.ramify.model.place.xml.place.uk.manor;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.id.Spid;
import net.ramify.model.place.region.iso.CountryIso;
import net.ramify.model.place.region.manor.Manor;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;
import net.ramify.model.place.xml.place.region.XmlForest;
import net.ramify.model.place.xml.place.uk.XmlUkPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "manor")
public class XmlManor extends XmlPlace {

    @XmlElementRefs({
            @XmlElementRef(type = XmlManor.class),
            @XmlElementRef(type = XmlGraveship.class),
            @XmlElementRef(type = XmlForest.class)
    })
    private List<XmlPlace> children;

    @Override
    protected PlaceId placeId(final String id, final CountryIso iso) {
        return new Spid(iso, Manor.class, id);
    }

    @Override
    protected Manor place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Manor(this.placeId(context), this.name(), parent, groupId, history);
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
