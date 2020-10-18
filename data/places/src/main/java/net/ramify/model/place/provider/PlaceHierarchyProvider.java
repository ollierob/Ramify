package net.ramify.model.place.provider;

import com.google.common.collect.Iterables;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.Place;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.hierarchy.PlaceHierarchy;
import net.ramify.model.place.hierarchy.PlaceHierarchyId;
import net.ramify.model.util.provider.Provider;

import javax.annotation.Nonnull;
import java.util.Set;

public interface PlaceHierarchyProvider extends Provider<PlaceHierarchyId, PlaceHierarchy> {

    @Nonnull
    Set<PlaceHierarchy> hierarchiesAbove(PlaceId placeId);

    @Nonnull
    default Set<PlaceHierarchy> hierarchiesAbove(final HasPlaceId place) {
        return this.hierarchiesAbove(place.placeId());
    }

    @Nonnull
    Set<? extends PlaceHierarchy> hierarchiesBelow(PlaceId placeId, int depth);

    default boolean isParentChild(final HasPlaceId parent, final HasPlaceId child) {
        final var hierarchy = this.hierarchiesAbove(child);
        return Iterables.any(hierarchy, h -> h.isChildOf(parent));
    }

    default boolean isChildParent(final HasPlaceId child, final HasPlaceId parent) {
        return this.isParentChild(parent, child);
    }

    default boolean hasParent(final HasPlaceId placeId) {
        return !this.hierarchiesAbove(placeId).isEmpty();
    }

    default boolean hasParent(final HasPlaceId placeId, final Class<? extends Place> placeType, final PlaceProvider places) {
        final var hierarchies = this.hierarchiesAbove(placeId);
        if (hierarchies.isEmpty()) return false;
        for (final var hierarchy : hierarchies) {
            for (final var id : hierarchy) {
                final var place = places.get(id);
                if (place != null && place.is(placeType)) return true;
            }
        }
        return false;
    }

}
