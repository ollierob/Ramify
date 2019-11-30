package net.ramify.model.place;

import net.ramify.data.proto.BuildsProto;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.PlaceHandler;
import net.ramify.utils.objects.Castable;
import net.ramify.utils.objects.Consumers;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public interface Place extends HasPlaceId, Castable<Place>, BuildsProto<PlaceProto.Place> {

    @Nonnull
    String name();

    @CheckForNull
    Place parent();

    @CheckForNull
    PlaceGroupId groupId();

    @Nonnull
    default Optional<String> iso() {
        return Optional.empty();
    }

    @CheckForNull
    PlaceHistory history();

    @Nonnull
    default Place ultimateParent() {
        final var parent = this.parent();
        return parent == null ? this : parent.ultimateParent();
    }

    default boolean isParentOf(final Place that) {
        if (that == null) return false;
        if (this.equals(that)) return true;
        return this.isParentOf(that.parent());
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
    default Set<Class<? extends Place>> childTypes() {
        return Collections.emptySet();
    }

    default boolean isDefunct() {
        return false;
    }

    <R> R handleWith(@Nonnull PlaceHandler<R> handler);

    @Nonnull
    PlaceProto.PlaceType protoType();

    @Nonnull
    default PlaceProto.Place.Builder toProtoBuilder(final boolean includeParent) {
        final var builder = PlaceProto.Place.newBuilder()
                .setId(this.placeId().value())
                .setName(this.name())
                .setType(this.protoType())
                .setDefunct(this.isDefunct());
        if (includeParent) Consumers.ifNonNull(this.parent(), parent -> builder.setParent(parent.toProto()));
        this.iso().ifPresent(builder::setIso);
        Consumers.ifNonNull(this.groupId(), groupId -> builder.setGroupId(groupId.value()));
        Consumers.ifNonNull(this.history(), history -> builder.setHistory(history.toProto()));
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

    Comparator<Place> SORT_BY_NAME = Comparator.comparing(Place::name);

}
