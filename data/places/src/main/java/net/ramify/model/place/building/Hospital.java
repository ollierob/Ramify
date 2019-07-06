package net.ramify.model.place.building;

import net.ramify.model.place.AbstractPlace;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.SettlementOrRegion;

import java.util.Objects;

public class Hospital extends AbstractPlace implements Institution {

    private final SettlementOrRegion parent;

    public Hospital(final PlaceId id, final String name, final Place parent) throws InvalidPlaceTypeException {
        this(id, name, parent.requireAs(SettlementOrRegion.class));
    }

    public Hospital(final PlaceId id, final String name, final SettlementOrRegion parent) {
        super(id, name);
        this.parent = Objects.requireNonNull(parent, "parent");
    }

    @Override
    public SettlementOrRegion parent() {
        return parent;
    }

    @Override
    public PlaceProto.PlaceType protoType() {
        return PlaceProto.PlaceType.HOSPITAL;
    }

}
