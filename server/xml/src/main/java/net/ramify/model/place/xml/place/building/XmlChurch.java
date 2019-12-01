package net.ramify.model.place.xml.place.building;

import net.ramify.model.ParserContext;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.type.BuildingHistory;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "church")
public class XmlChurch extends XmlBuilding<Church> {

    @XmlElementRef(required = false)
    private List<XmlGraveyard> graveyards;

    XmlChurch() {
        super(Church.class);
    }

    @Override
    protected Church place(final Place parent, final PlaceGroupId groupId, final BuildingHistory history, final ParserContext context) throws Place.InvalidPlaceTypeException {
        return new Church(this.placeId(), this.name(), parent, groupId, history);
    }

    @Override
    protected Collection<? extends XmlPlace> children() {
        return graveyards == null ? Collections.emptySet() : graveyards;
    }

}