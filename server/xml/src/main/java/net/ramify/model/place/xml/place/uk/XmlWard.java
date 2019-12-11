package net.ramify.model.place.xml.place.uk;

import com.google.common.base.MoreObjects;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.region.Hundred;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlArea;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlUkPlace.NAMESPACE, name = "ward")
class XmlWard extends XmlArea<Hundred> {

    static final PlaceProto.PlaceType TYPE = PlaceProto.PlaceType.newBuilder().setName("Ward").setCanPrefix(true).setCanSuffix(true).build();

    @XmlElementRefs({
            @XmlElementRef(type = XmlParish.class),
    })
    private List<XmlPlace> children;

    XmlWard() {
        super(Hundred.class);
    }

    @Override
    protected Hundred place(final Place parent, final PlaceGroupId groupId, final PlaceHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        return new Hundred(this.placeId(context), this.name(), parent, groupId, history, TYPE);
    }

    @Override
    protected Collection<XmlPlace> children() {
        return MoreObjects.firstNonNull(children, Collections.emptyList());
    }

}