package net.ramify.model.place;

import net.meerkat.functions.consumers.Consumers;
import net.meerkat.objects.Castable;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.Iso;
import net.ramify.model.place.history.PlaceHistory;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.model.place.type.PlaceHandler;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public interface Place extends HasPlaceId, Castable<Place>, BuildsProto<PlaceProto.Place> {

    @Nonnull
    String name();

    @Nonnull
    default Optional<? extends Iso> iso() {
        return Optional.empty();
    }

    @CheckForNull
    PlaceHistory history();

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
    default PlaceProto.Place.Builder toProtoBuilder() {
        final var builder = PlaceProto.Place.newBuilder()
                .setId(this.placeId().value())
                .setGroupId(this.placeGroupId().value())
                .setName(this.name())
                .setType(this.protoType())
                .setDefunct(this.isDefunct());
        this.iso().map(Iso::value).ifPresent(builder::setIso);
        Consumers.ifNonNull(this.history(), history -> builder.setHistory(history.toProto()));
        return builder;
    }

    @Nonnull
    @Override
    default PlaceProto.Place toProto() {
        return this.toProtoBuilder().build();
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
