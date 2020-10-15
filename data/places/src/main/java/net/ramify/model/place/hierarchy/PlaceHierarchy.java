package net.ramify.model.place.hierarchy;

import net.ollie.protobuf.BuildsProto;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.Iterator;

public interface PlaceHierarchy extends HasPlaceId, Iterable<PlaceId>, BuildsProto<PlaceProto.PlaceHierarchy> {

    @Nonnull
    PlaceHierarchyId id();

    @Override
    PlaceId placeId();

    @CheckForNull
    PlaceHierarchy parent();

    default boolean is(final HasPlaceId place) {
        return this.placeId().equals(place.placeId());
    }

    default boolean isChildOf(final HasPlaceId place) {
        if (this.is(place)) return true;
        final var parent = this.parent();
        return parent != null && parent.isChildOf(place);
    }

    @Override
    default Iterator<PlaceId> iterator() {
        return new Iterator<>() {

            PlaceHierarchy hierarchy = PlaceHierarchy.this;

            @Override
            public boolean hasNext() {
                return hierarchy != null;
            }

            @Override
            public PlaceId next() {
                final var next = hierarchy.placeId();
                hierarchy = hierarchy.parent();
                return next;
            }
        };
    }

    @Nonnull
    @Override
    default PlaceProto.PlaceHierarchy toProto() {
        return this.toProtoBuilder().build();
    }

    @Nonnull
    default PlaceProto.PlaceHierarchy.Builder toProtoBuilder() {
        final var builder = PlaceProto.PlaceHierarchy.newBuilder().setId(this.id().value());
        this.forEach(id -> builder.addPlace(PlaceProto.Place.newBuilder().setId(id.value())));
        return builder;
    }

}
