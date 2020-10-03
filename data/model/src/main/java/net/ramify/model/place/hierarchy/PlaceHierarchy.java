package net.ramify.model.place.hierarchy;

import net.ramify.model.place.HasPlaceId;
import net.ramify.model.place.PlaceId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public interface PlaceHierarchy extends HasPlaceId {

    @Nonnull
    PlaceHierarchyId id();

    @Override
    PlaceId placeId();

    @CheckForNull
    PlaceHierarchy parent();

    default boolean isParent(final HasPlaceId place) {
        if (this.placeId().equals(place.placeId())) return true;
        final var parent = this.parent();
        return parent != null && parent.isParent(place);
    }

}
