package net.ramify.model.place;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.PlaceHandler;
import net.ramify.utils.objects.Castable;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Optional;

public interface Place extends HasPlaceId, Castable<Place>, BuildsProto<PlaceProto.Place> {

    @Nonnull
    String name();

    @CheckForNull
    Place parent();

    default boolean contains(final Place that) {
        if (that == null) return false;
        if (this.equals(that)) return true;
        return this.contains(that.parent());
    }

    @Nonnull
    default <P extends Place> Optional<P> find(final Class<P> type) {
        if (this.is(type)) return this.as(type);
        final var parent = this.parent();
        if (parent == null) return Optional.empty();
        return parent.find(type);
    }

    @Nonnull
    default String address() {
        var address = this.name();
        final var parent = this.parent();
        return parent == null ? address : address + ", " + parent.address();
    }

    <R> R handleWith(PlaceHandler<R> handler);

    default PlaceProto.Place.Builder toProtoBuilder() {
        return PlaceProto.Place.newBuilder()
                .setName(this.name())
                .setAddress(this.address());
    }

    @Nonnull
    @Override
    default PlaceProto.Place toProto() {
        return this.toProtoBuilder().build();
    }

}
