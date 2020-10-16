package net.ramify.model.place.hierarchy;

import net.ramify.model.place.PlaceId;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class ParentChildHierarchy implements PlaceHierarchy {

    private final PlaceId parent;
    private final PlaceId child;

    public ParentChildHierarchy(final PlaceId parent, final PlaceId child) {
        this.parent = parent;
        this.child = child;
    }

    @Nonnull
    @Override
    public PlaceHierarchyId id() {
        return PlaceHierarchyId.of(parent, child);
    }

    @Override
    public PlaceId placeId() {
        return child;
    }

    @CheckForNull
    public PlaceId parentId() {
        return parent;
    }

    @Nonnull
    public PlaceId childId() {
        return child;
    }

    @CheckForNull
    @Override
    public ParentChildHierarchy parent() {
        return parent == null ? null : new ParentChildHierarchy(null, parent);
    }

}
