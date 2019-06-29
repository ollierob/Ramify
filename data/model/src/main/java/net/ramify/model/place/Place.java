package net.ramify.model.place;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.PlaceHandler;
import net.ramify.utils.objects.Castable;
import net.ramify.utils.objects.Consumers;

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
        final var builder = PlaceProto.Place.newBuilder()
                .setId(this.placeId().value())
                .setName(this.name())
                .setType(this.type());
        Consumers.ifNonNull(this.parent(), parent -> builder.setParent(parent.toProto()));
        return builder;
    }

    default String type() {
        return this.getClass().getSimpleName();
    }

    @Nonnull
    @Override
    default PlaceProto.Place toProto() {
        return this.toProtoBuilder().build();
    }

}
