package net.ramify.model.place.xml.place.building;

import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceGroupId;
import net.ramify.model.place.building.Church;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.type.SettlementOrRegion;
import net.ramify.model.place.xml.PlaceParserContext;
import net.ramify.model.place.xml.place.XmlPlace;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@XmlRootElement(namespace = XmlPlace.NAMESPACE, name = "church")
public class XmlChurch extends XmlBuilding<Church> {

    @XmlElementRef(required = false)
    private List<XmlGraveyard> graveyards;

    XmlChurch() {
        super(Church.class);
    }

    @Override
    protected Church toPlace(final Place parent, final PlaceGroupId groupId, final BuildingHistory history, final PlaceParserContext context) throws Place.InvalidPlaceTypeException {
        Objects.requireNonNull(parent, "parent");
        return new Church(this.placeId(context), this.name(), parent.requireAs(SettlementOrRegion.class), groupId, history);
    }

    @Override
    protected Collection<? extends XmlPlace> children() {
        return graveyards == null ? Collections.emptySet() : graveyards;
    }

}
