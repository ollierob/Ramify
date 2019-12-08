package net.ramify.model.place.type;

import net.ramify.model.place.Place;
import net.ramify.model.place.history.BuildingHistory;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Set;

public interface Building extends Place {

    @CheckForNull
    @Override
    BuildingHistory history();

    /**
     * @return true if the building is not used for its purpose any more.
     */
    @Override
    boolean isDefunct();

    @Nonnull
    @Override
    default Set<Class<? extends Place>> childTypes() {
        return Collections.emptySet();
    }

    @Override
    default <R> R handleWith(final PlaceHandler<R> handler) {
        return handler.handle(this);
    }

    @Nonnull
    @Override
    default PlaceProto.Place.Builder toProtoBuilder(final boolean includeParent) {
        final var builder = Place.super.toProtoBuilder(includeParent);
        builder.getTypeBuilder().setIsBuilding(true);
        return builder;
    }

}
