package net.ramify.model.place.hierarchy;

import com.google.common.collect.Sets;
import net.ollie.protobuf.BuildsProto;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.proto.PlaceProto;
import net.ramify.utils.objects.Functions;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
    default Set<PlaceId> placeIds() {
        return Sets.newHashSet(this.iterator());
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

    static PlaceHierarchy ofChildrenToParent(final List<PlaceId> places) {
        if (places.size() <= 1) return ofParentToChildren(places);
        final var reversed = new ArrayList<>(places);
        Collections.reverse(reversed);
        return ofParentToChildren(places);
    }

    static PlaceHierarchy ofParentToChildren(final List<PlaceId> places) {
        //TODO use list iterator if not RandomAccess
        PlaceHierarchy parent = null;
        for (int i = 0; i < places.size(); i++) {
            final var childId = places.get(i);
            final var hierarchyId = PlaceHierarchyId.of(Functions.ifNonNull(parent, HasPlaceId::placeId), childId);
            parent = new ChildParentHierarchy(hierarchyId, childId, parent);
        }
        return parent;
    }

    static Set<PlaceId> ids(final Set<? extends PlaceHierarchy> hierarchies) {
        if (hierarchies.isEmpty()) return Collections.emptySet();
        final var set = Sets.<PlaceId>newHashSetWithExpectedSize(hierarchies.size() * 2);
        hierarchies.forEach(h -> set.addAll(h.placeIds()));
        return set;
    }

}
