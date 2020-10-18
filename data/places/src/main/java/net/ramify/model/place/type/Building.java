package net.ramify.model.place.type;

import net.ramify.model.place.Place;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface Building extends Place {

    @CheckForNull
    @Override
    BuildingHistory history();

    /**
     * @return true if the building is not used for its purpose any more.
     */
    @Override
    boolean isDefunct();

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default PlaceProto.Place.Builder toProtoBuilder() {
        final var builder = Place.super.toProtoBuilder();
        builder.getTypeBuilder().setIsBuilding(true);
        return builder;
    }

}
