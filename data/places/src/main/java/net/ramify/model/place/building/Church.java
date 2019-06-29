package net.ramify.model.place.building;

import net.ramify.model.place.AbstractPlace;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.type.Building;
import net.ramify.model.place.type.SettlementOrRegion;

import javax.annotation.CheckForNull;

public class Church extends AbstractPlace implements Building {

    private final SettlementOrRegion parent;

    public Church(final PlaceId id, final String name, final Place parent) {
        this(id, name, parent.requireAs(SettlementOrRegion.class));
    }

    public Church(final PlaceId id, final String name, final SettlementOrRegion parent) {
        super(id, name);
        this.parent = parent;
    }

    @CheckForNull
    @Override
    public SettlementOrRegion parent() {
        return parent;
    }

}
