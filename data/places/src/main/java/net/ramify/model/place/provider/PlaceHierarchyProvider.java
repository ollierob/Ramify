package net.ramify.model.place.provider;

import com.google.common.collect.Iterables;
import net.ramify.model.place.HasPlace;
import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;
import net.ramify.model.place.hierarchy.PlaceHierarchy;
import net.ramify.model.place.hierarchy.PlaceHierarchyId;
import net.ramify.model.util.provider.Provider;

import javax.annotation.Nonnull;
import java.util.Set;

public interface PlaceHierarchyProvider extends Provider<PlaceHierarchyId, PlaceHierarchy> {

    @Nonnull
    Set<PlaceHierarchy> hierarchies(PlaceId placeId);

    @Nonnull
    default Set<PlaceHierarchy> hierarchies(final HasPlaceId place) {
        return this.hierarchies(place.placeId());
    }

    @Nonnull
    Set<PlaceId> findByName(String name, int limit, PlaceId within);

    default boolean isParent(final HasPlace parent, final HasPlace child) {
        final var hierarchy = this.hierarchies(child);
        return Iterables.any(hierarchy, h -> h.isParent(parent));
    }

}
