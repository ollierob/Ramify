package net.ramify.model.place;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.PlaceHandler;
import net.ramify.utils.objects.Castable;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

public interface Place extends HasPlaceId, Castable<Place>, BuildsProto<PlaceProto.Place> {

    @Nonnull
    String name();

    @CheckForNull
    Place parent();

    @Nonnull
    default Place ultimateParent() {
        final var parent = this.parent();
        return parent == null ? this : parent.ultimateParent();
    }

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

    @Nonnull
    Set<Class<? extends Place>> childTypes();

    <R> R handleWith(@Nonnull PlaceHandler<R> handler);

    @Nonnull
    PlaceProto.PlaceType protoType();

    @Nonnull
    default PlaceProto.Place.Builder toProtoBuilder(final boolean includeParent) {
        final var builder = PlaceProto.Place.newBuilder()
                .setId(this.placeId().value())
                .setName(this.name())
                .setType(this.protoType());
        if (includeParent) Consumers.ifNonNull(this.parent(), parent -> builder.setParent(parent.toProto()));
        return builder;
    }

    @Nonnull
    default PlaceProto.Place toProto(final boolean includeParent) {
        return this.toProtoBuilder(includeParent).build();
    }

    @Nonnull
    @Override
    default PlaceProto.Place toProto() {
        return this.toProto(true);
    }

    default <R extends Place> R requireAs(final Class<? extends R> type) throws InvalidPlaceTypeException {
        final var cast = this.as(type).orElse(null);
        if (cast == null) throw new InvalidPlaceTypeException(type, this.getClass());
        return cast;
    }

    class InvalidPlaceTypeException extends Exception {

        public InvalidPlaceTypeException(final Class<?> expected, final Class<?> actual) {
            super("Invalid type: expected " + expected.getSimpleName() + " but was " + actual.getSimpleName());
        }

    }

}
