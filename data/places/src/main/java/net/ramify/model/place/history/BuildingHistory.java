package net.ramify.model.place.history;

import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.Nonnull;

public interface BuildingHistory extends PlaceHistory {

    boolean isDemolished();

    @Nonnull
    @Override
    default PlaceProto.PlaceHistory.Builder toProtoBuilder() {
        return PlaceHistory.super.toProtoBuilder().setDemolished(this.isDemolished());
    }

}
